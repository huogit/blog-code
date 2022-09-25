package com.prestrive.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.prestrive.blog.common.lang.vo.PageComment;
import com.prestrive.blog.entity.Comment;

import java.util.List;

/**
 * 服务类
 *
 * @author fanfanli
 * @date  2021/4/8
 */
public interface CommentService extends IService<Comment> {

    /**
     * 通过博客id和父评论id查找所有子评论 并按照时间倒序排序
     *
     * @param blogId
     * @param parentCommentId
     * @return
     */
    public List<PageComment> getPageCommentListByDesc(Long blogId, Long parentCommentId);

    /**
     * 通过博客id和父评论id查找所有子评论
     *
     * @param blogId
     * @param parentCommentId
     * @return
     */
    public List<PageComment> getPageCommentList(Long blogId, Long parentCommentId);
}
