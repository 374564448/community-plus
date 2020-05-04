package com.banmingi.communityplus.usercenter.test;

import com.banmingi.communityplus.usercenter.entity.User;
import com.banmingi.communityplus.usercenter.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTest {

    @Autowired
    private  UserMapper userMapper;

    @Test
    public void testSelectUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id","123456");
        User user = this.userMapper.selectOne(wrapper);
        System.out.println(user);
    }
    @Test
    public void testInsert() {
        User user = User.builder()
                .accountId("15117326032")
                .accountType(1)
                .password(DigestUtils.md5DigestAsHex("aaa12345678".getBytes()))
                .name("banmingi")
                .avatarUrl("https://avatars1.githubusercontent.com/u/51524441?v=4")
                .bio("等风来不如追风去")
                .roles("user")
                .createTime(new Date())
                .modifyTime(new Date())
                .build();
        this.userMapper.insert(user);
        System.out.println(user.getPassword());
    }

}
