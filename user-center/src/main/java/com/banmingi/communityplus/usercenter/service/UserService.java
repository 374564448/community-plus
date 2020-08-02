package com.banmingi.communityplus.usercenter.service;

import com.banmingi.communityplus.usercenter.dto.user.GeneralLoginDTO;
import com.banmingi.communityplus.usercenter.dto.user.GitHubLoginDTO;
import com.banmingi.communityplus.usercenter.dto.user.RegisterDTO;
import com.banmingi.communityplus.usercenter.dto.user.UserAddBonusMsgDTO;
import com.banmingi.communityplus.usercenter.dto.user.UserDTO;
import com.banmingi.communityplus.usercenter.entity.BonusEventLog;
import com.banmingi.communityplus.usercenter.entity.User;
import com.banmingi.communityplus.usercenter.enums.AccountTypeEnum;
import com.banmingi.communityplus.usercenter.enums.RegisterRespStatusEnum;
import com.banmingi.communityplus.usercenter.mapper.BonusEventLogMapper;
import com.banmingi.communityplus.usercenter.mapper.UserMapper;
import com.banmingi.communityplus.usercenter.rocketmq.output.CheckCodeSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;
    private final CheckCodeSource checkCodeSource;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY_PREFIX = "user:checkCode:";

    /**
     * 发送验证码
     * @param accountId
     */
    public void sendCheckCode(String accountId) {
        if (StringUtils.isBlank(accountId)){
            return;
        }
        //生成6位随机验证码
        String checkCode = Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
        //发送消息到消息队列
        Map<String,String> msg = new HashMap<>();
        msg.put("accountId",accountId);
        msg.put("checkCode",checkCode);
        this.checkCodeSource.output().send(MessageBuilder.withPayload(msg).build());

        //把验证码保存到redis中,5分钟有效
        this.redisTemplate.opsForValue().set(KEY_PREFIX+accountId,checkCode,5L, TimeUnit.MINUTES);
    }

    /**
     * 账号密码登录
     * @param generalLoginDTO 账号密码登录DTO
     * @return
     */
    public User generalLogin(GeneralLoginDTO generalLoginDTO) {
        //根据accountId查询用户
        User user = findUserByAccountId(generalLoginDTO.getAccountId());
        String password = DigestUtils.md5DigestAsHex(generalLoginDTO.getPassword().getBytes());
        //如果用户不存在或者密码验证不正确
        if (user == null|| !password.equals(user.getPassword())) {
            return null;
        }
        return user;
    }

    /**
     * GitHub账户登录
     *
     * @param gitHubLoginDTO gitHub登录DTO
     * @return
     */
    public User gitHubLogin(GitHubLoginDTO gitHubLoginDTO) {
        //根据accountId查询用户
        User user = findUserByAccountId(gitHubLoginDTO.getAccountId());
        //如果数据库中没有该用户,则初始化并插入该用户
        if (user == null) {
            User userSave = new User();
            BeanUtils.copyProperties(gitHubLoginDTO,userSave);
            userSave.setAccountType(AccountTypeEnum.GitHub.getAccountType());
            userSave.setRoles("user");
            userSave.setBonus(0);
            userSave.setCreateTime(System.currentTimeMillis());
            userSave.setModifyTime(System.currentTimeMillis());
            this.userMapper.insert(userSave);

            return userSave;
        }
        //否则把数据库中的用户返回
        user.setModifyTime(System.currentTimeMillis());
        this.userMapper.updateById(user);

        return user;
    }

    /**
     * 根据accountId查询用户
     * @param accountId
     * @return
     */
    public User findUserByAccountId(String accountId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        return this.userMapper.selectOne(wrapper);
    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public UserDTO findById(Integer id) {
        User user = this.userMapper.selectById(id);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

    /**
     * 注册用户.
     * @param registerDTO
     * @return
     */
    public Integer register(RegisterDTO registerDTO) {
        //查询redis比较验证码
        String redisCodeKey = KEY_PREFIX + registerDTO.getAccountId();
        String redisCode = this.redisTemplate.opsForValue().get(redisCodeKey);
        if (!StringUtils.equals(registerDTO.getCheckCode(),redisCode)) {
            return RegisterRespStatusEnum.CHECK_CODE_ERROR.getStatus();
        }
        //根据accountId查询用户
        User user = findUserByAccountId(registerDTO.getAccountId());
        //用户已存在
        if (user != null) {
            return RegisterRespStatusEnum.ACCOUNT_BEING.getStatus();
        }
        //创建用户
        String defaultName = "用户" + UUID.randomUUID().toString().substring(0,5);//默认昵称
        String defaultAvatarUrl
                = "http://banmingi-community-plus.oss-cn-qingdao.aliyuncs.com/fcec0af8-a8b8-462b-af32-90ca9a0b877e.png"; //默认头像
        String password = DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes());
        User userRegister = User.builder()
                .accountId(registerDTO.getAccountId())
                .accountType(AccountTypeEnum.General.getAccountType())
                .password(password)
                .name(defaultName)
                .avatarUrl(defaultAvatarUrl)
                .roles("user")
                .bonus(0)
                .createTime(System.currentTimeMillis())
                .modifyTime(System.currentTimeMillis())
                .build();
        this.userMapper.insert(userRegister);
        //最后移除redis的checkCode
        this.redisTemplate.delete(redisCodeKey);

        return  RegisterRespStatusEnum.SUCCESS.getStatus();
    }


    /**
     * 为用户添加积分、记录日志
     * @param userAddBonusMsgDTO 积分添加的实体信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        //1. 为用户添加积分
        Integer userId = userAddBonusMsgDTO.getUserId();
        Integer bonus = userAddBonusMsgDTO.getBonus();
        User user = this.userMapper.selectById(userId);
        user.setBonus(user.getBonus() + bonus);
        this.userMapper.updateById(user);

        //2. 记录日志到bonus_event_log 里面
        BonusEventLog bonusEventLog = new BonusEventLog();
        BeanUtils.copyProperties(userAddBonusMsgDTO,bonusEventLog);
        bonusEventLog.setCreateTime(System.currentTimeMillis());
        this.bonusEventLogMapper.insert(bonusEventLog);
        log.info("积分添加完毕");
    }

    /**
     * 根据用户id集合查询用户信息集合
     * @param ids 用户id集合
     * @return 用户信息集合
     */
    public List<UserDTO> findListByIds(List<Integer> ids) {
        List<User> userList = this.userMapper.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            return userDTO;
        }).collect(Collectors.toList());
    }
}
