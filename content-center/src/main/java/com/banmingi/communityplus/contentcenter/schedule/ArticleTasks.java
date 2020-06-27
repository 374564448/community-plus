package com.banmingi.communityplus.contentcenter.schedule;

import com.banmingi.communityplus.contentcenter.dto.article.ArticleDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.entity.CacheDataToDbEventLog;
import com.banmingi.communityplus.contentcenter.enums.CacheDataToDbTypeEnum;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import com.banmingi.communityplus.contentcenter.mapper.CacheDataToDbEventLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;

/**
 * @author 半命i 2020/5/25
 * @description 定时任务: 文章缓存数据定时存入数据库
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleTasks {

    private final ArticleMapper articleMapper;
    private final RedisTemplate<String,Object> redisTemplate;
    private final CacheDataToDbEventLogMapper cacheDataToDbEventLogMapper;
    private static final String ARTICLE_ID_KEY = "article:id:";

    /**
     * 每天0点 12点把缓存中存在的文章数据信息更新到数据库中
     */
    @Scheduled(cron = "0 0 0,12 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleCacheToDB() {
        log.info("The time is now {}", new Date());

        //查询缓存中以article:id:开头的key
        Set<String> keys = this.redisTemplate.keys(ARTICLE_ID_KEY + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        keys.forEach(key-> {
            //先从缓存中查询文字详情
            ArticleDTO articleDTO = (ArticleDTO) this.redisTemplate.opsForValue().get(key);
            Article article = new Article();
            if (articleDTO!=null) {
                BeanUtils.copyProperties(articleDTO,article);
            }
            //更新文章到数据库中
            this.articleMapper.updateById(article);
            //记录日志
            //取出文章id
            Integer id = Integer.valueOf(key.split(":")[key.split(":").length]);
            CacheDataToDbEventLog cacheDataToDbEventLog
                    = CacheDataToDbEventLog.builder()
                    .dataId(id)
                    .event(CacheDataToDbTypeEnum.ARTICLE.getEvent())
                    .description(CacheDataToDbTypeEnum.ARTICLE.getDescription())
                    .createTime(System.currentTimeMillis())
                    .build();
            this.cacheDataToDbEventLogMapper.insert(cacheDataToDbEventLog);
        });


    }
}
