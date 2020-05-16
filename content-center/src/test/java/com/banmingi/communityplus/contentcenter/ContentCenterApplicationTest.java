package com.banmingi.communityplus.contentcenter;

import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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



}
