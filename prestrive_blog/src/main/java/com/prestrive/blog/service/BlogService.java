package com.prestrive.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.prestrive.blog.common.lang.vo.BlogInfo;
import com.prestrive.blog.entity.Blog;

import java.util.List;

/**
 * 服务类
 *
 * @author fanfanli
 * @date  2021/4/5
 */
public interface BlogService extends IService<Blog> {

    /**
     * 通过分类名查找属于该分类的博客list
     *
     * @param categoryName 分类名
     * @return 博客list
     */
    List<BlogInfo> getBlogInfoListByCategoryName(String categoryName);
}
