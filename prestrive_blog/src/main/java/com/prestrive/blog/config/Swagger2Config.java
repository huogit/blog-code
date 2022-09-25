package com.prestrive.blog.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //自动装配 swagger
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){
        //Docket 摘要
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")//分组
                .apiInfo(webApiInfo()) //api文档的描述
                //路径过滤器
                .select()
//                //过滤：只显示api路径下的页面
//                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

//    @Bean
//    public Docket adminApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("adminApi")
//                .apiInfo(webApiInfo())
//
//                .select()
//                //只显示admin路径下的页面
//                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
//                .build();
//    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("网站-API文档")
                .description("本文档描述了网站微服务接口定义")
                .version("1.0")
                .contact(new Contact("strive", "http://prestrive.com", "1801465024@qq.com"))
                .build();
    }


//    private ApiInfo adminApiInfo(){
//        return new ApiInfoBuilder()
//                .title("后台管理系统-API文档")
//                .description("本文档描述了后台管理系统微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("strive", "http://prestrive.com", "1801465024@qq.com"))
//                .build();
//    }
}
