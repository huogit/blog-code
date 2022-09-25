package com.prestrive.blog.Schedule;

import com.prestrive.blog.config.RedisKeyConfig;
import com.prestrive.blog.entity.Blog;
import com.prestrive.blog.service.BlogService;
import com.prestrive.blog.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 定时任务
 * @author: fanfanli
 * @date: 2021/8/11
 */
@Component
@EnableScheduling
@EnableAsync
public class RedisSyncScheduleTask {

    @Autowired
    RedisService redisService;
    @Autowired
    BlogService blogService;

    Logger logger = LoggerFactory.getLogger(RedisSyncScheduleTask.class);

    /**
     * 从Redis同步博客文章浏览量到数据库
     */
    @Async
    @Scheduled(fixedDelay = 24*60*60*1000)  //间隔24小时秒
    public void syncBlogViewsToDatabase() {
        logger.info("执行定时任务");
        // 获取 redis 中 key
        String redisKey = RedisKeyConfig.BLOG_VIEWS_MAP;
        // 根据 key 获取 值（Hash 数据类型）
        Map blogViewsMap = redisService.getMapByHash(redisKey);
        //  keySet() 返回map中所有key值的列表
        Set<Integer> keys = blogViewsMap.keySet();
        // 循环 取出 key
        for (Integer key : keys) {
            // 获取 Map 中 key 的值
            Integer views = (Integer) blogViewsMap.get(key);
            // 获取 博客文章
            Blog blog = blogService.getById(key);
            // 添加浏览量
            blog.setViews(blog.getViews()+views);
            // 保存 或 更新
            blogService.saveOrUpdate(blog);
        }
        // 删除 所有 的键
        deleteAllCache();
        logger.info("完成任务");
    }

    /**
     * 清除所有缓存
     */
//    @Async
//    @Scheduled(fixedDelay = 60*1000)
    public void deleteAllCache() {
        System.out.println("RedisSyncScheduleTask:deleteAllCache.......");
        redisService.deleteAllKeys();
    }


}
