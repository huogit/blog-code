package com.prestrive.blog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * MybatisPlus配置
 *
 * @author fanfanli
 * @date  2021/4/8
 */
@Configuration
@EnableTransactionManagement
//
@MapperScan("com.prestrive.blog.mapper")
public class MybatisPlusConfig {
    /**
     * 配置分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        System.out.println("MybatisPlusConfig:paginationInterceptor......");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}

