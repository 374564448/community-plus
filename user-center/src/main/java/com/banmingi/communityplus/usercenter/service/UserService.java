package com.banmingi.communityplus.usercenter.service;

import com.banmingi.communityplus.usercenter.dto.login.GeneralLoginDTO;
import com.banmingi.communityplus.usercenter.dto.login.GitHubLoginDTO;
import com.banmingi.communityplus.usercenter.entity.User;
import com.banmingi.communityplus.usercenter.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMapper userMapper;

    /**
     * 账号密码登录
     * @param generalLoginDTO
     * @return
     */
    public User generalLogin(GeneralLoginDTO generalLoginDTO) {
        //根据accountId查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", generalLoginDTO.getAccountId());
        User user = this.userMapper.selectOne(wrapper);
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", gitHubLoginDTO.getAccountId());
        User user = this.userMapper.selectOne(wrapper);

        //如果数据库中没有该用户,则初始化并插入该用户
        if (user == null) {
            User userSave = new User();
            BeanUtils.copyProperties(gitHubLoginDTO,userSave);
            userSave.setAccountType(2);
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
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findById(Integer id) {
        return this.userMapper.selectById(id);
    }


}
