package com.prestrive.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.prestrive.blog.entity.Tag;
import com.prestrive.blog.mapper.TagMapper;
import com.prestrive.blog.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author fanfanli
 * @date 2021-04-08
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
