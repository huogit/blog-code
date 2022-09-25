package com.prestrive.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.prestrive.blog.entity.VisitLog;
import com.prestrive.blog.mapper.VisitLogMapper;
import com.prestrive.blog.service.VisitLogService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author fanfanli
 * @date 2021-04-08
 */
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

}
