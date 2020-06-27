package com.banmingi.communityplus.contentcenter;

import com.banmingi.communityplus.contentcenter.dto.comment.CommentDTO;
import com.banmingi.communityplus.contentcenter.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
    private CommentService commentService;


    @Test
    public void test() {
        //查询缓存中以article:id:开头的key
        Set<String> keys = this.redisTemplate.keys("article:id:*");
        keys.forEach( key -> {
            System.out.println(key);
            this.redisTemplate.delete(key);
        });

    }
    @Test
    public void test2() {
        List<CommentDTO> commentList = this.commentService.getCommentList(13);
        commentList.forEach(commentDTO -> {
            System.out.println(commentDTO);
            commentDTO.getCommentSecondDTOList().forEach(System.out::println);
        });
    }


}
