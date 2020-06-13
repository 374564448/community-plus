package com.banmingi.communityplus.contentcenter;

import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @auther 半命i 2020/5/15
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentCenterApplicationTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test() {
        //查询缓存中以article:id:开头的key
        Set<String> keys = this.redisTemplate.keys("article:id:*");
        keys.forEach( key -> {
            System.out.println(key);
            this.redisTemplate.delete(key);
        });

    }



}
