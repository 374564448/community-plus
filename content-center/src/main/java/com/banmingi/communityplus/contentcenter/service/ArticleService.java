package com.banmingi.communityplus.contentcenter.service;

import com.alibaba.fastjson.JSONObject;
import com.banmingi.communityplus.contentcenter.dto.ArticlePublishDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.enums.ArticleStatusEnum;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY_PREFIX = "article:save:";

    /**
     * 发布或编辑文章
     * @param articlePublishDTO
     */
    public void publishOrUpdate(ArticlePublishDTO articlePublishDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articlePublishDTO,article);
        if (article.getId() == null){
            article.setAuditStatus(ArticleStatusEnum.NOT_YET.getStatus());
            article.setCreateTime(new Date());
            article.setModifyTime(new Date());
            this.articleMapper.insert(article);
        } else {
            article.setModifyTime(new Date());
            this.articleMapper.updateById(article);
        }
        //移除redis中保存的文章
        this.redisTemplate.delete(KEY_PREFIX + articlePublishDTO.getUserId());
    }

    /**
     * 保存文章(暂时保存7天)
     * @param articlePublishDTO
     */
    public void save(ArticlePublishDTO articlePublishDTO) {
        Integer userId = articlePublishDTO.getUserId();
        String articleStr = JSONObject.toJSONString(articlePublishDTO);
        this.redisTemplate.opsForValue().set(KEY_PREFIX+userId,articleStr,7L, TimeUnit.DAYS);
    }

    /**
     * 获取保存的文章
     * @return
     */
   public ArticlePublishDTO getTheSavedArticle(Integer userId) {
       Boolean hasArticle = this.redisTemplate.hasKey(KEY_PREFIX + userId);
       if (!hasArticle) {
           return null;
       }
       String articleStr = this.redisTemplate.opsForValue().get(KEY_PREFIX + userId);
       return JSONObject.parseObject(articleStr, ArticlePublishDTO.class);
   }
}
