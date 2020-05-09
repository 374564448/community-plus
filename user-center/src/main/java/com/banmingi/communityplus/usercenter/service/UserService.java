package com.banmingi.communityplus.usercenter.service;

import com.banmingi.communityplus.usercenter.dto.GeneralLoginDTO;
import com.banmingi.communityplus.usercenter.dto.GitHubLoginDTO;
import com.banmingi.communityplus.usercenter.dto.RegisterDTO;
import com.banmingi.communityplus.usercenter.entity.User;
import com.banmingi.communityplus.usercenter.enums.AccountTypeEnum;
import com.banmingi.communityplus.usercenter.enums.RegisterRespStatusEnum;
import com.banmingi.communityplus.usercenter.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMapper userMapper;
    private final Source source;
    //private final RedisTemplate<String, String> redisTemplate;

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
        this.source.output().send(MessageBuilder.withPayload(msg).build());

        //把验证码保存到redis中
        //this.redisTemplate.opsForValue().set(KEY_PREFIX+accountId,checkCode);
    }

    /**
     * 账号密码登录
     * @param generalLoginDTO
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
     * @param gitHubLoginDTO
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
            userSave.setCreateTime(new Date());
            userSave.setModifyTime(new Date());
            this.userMapper.insert(userSave);

            return userSave;
        }
        //否则把数据库中的用户返回
        user.setModifyTime(new Date());
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
    public User findById(Integer id) {
        return this.userMapper.selectById(id);
    }

    /**
     * 注册用户.
     * @param registerDTO
     * @return
     */
    public Integer register(RegisterDTO registerDTO) {
        //TODO 查询redis比较验证码
        if (!registerDTO.getCheckCode().equals("111111")) {
            return RegisterRespStatusEnum.CHECK_CODE_ERROR.getStatus();
        }

        //根据accountId查询用户
        User user = findUserByAccountId(registerDTO.getAccountId());
        //用户已存在
        if (user != null) {
            return RegisterRespStatusEnum.ACCOUNT_BEING.getStatus();
        }
        //创建用户
        String defaultName = "用户" + UUID.randomUUID().toString().substring(0,5);
        String password = DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes());
        User userRegister = User.builder()
                .accountId(registerDTO.getAccountId())
                .accountType(AccountTypeEnum.General.getAccountType())
                .password(password)
                .name(defaultName)
                .roles("user")
                .bonus(0)
                .createTime(new Date())
                .modifyTime(new Date())
                .build();
        this.userMapper.insert(userRegister);
        return  RegisterRespStatusEnum.SUCCESS.getStatus();
    }
}
