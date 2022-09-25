package com.prestrive.blog.config;


import com.prestrive.blog.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 解决跨域问题的配置
 *
 * @author fanfanli
 * @date  2021/4/20
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {


    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    /**
     * 设置跨域
     * 使用此方法配置之后 再使用 自定义拦截器时 跨域相关配置就会失效。
     *  前提是：拦截器有没有用到 资源，如 token验证的拦截器使项目中配置的跨域配置失效
     * 原因是请求经过的先后顺序问题，当请求到来时会先进入拦截器中，而不是进入Mapping映射中，所以返回的头信息中并没有配置的跨域信息。浏览器就会报跨域异常。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CorsConfig:addCorsMappings......");
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

//    /**
//     * 跨域配置
//     * @return
//     */
//    private CorsConfiguration corsConfig() {
//        // 新建CORS配置
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        /*
//        请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
//         */
//        // 设置允许访问的请求源HTTP
//        //corsConfiguration.addAllowedOrigin("http://localhost:8080");
//        corsConfiguration.addAllowedOrigin("*");
//        // 设置允许访问的请求源头部信息
//        corsConfiguration.addAllowedHeader("*");
//        // 设置允许访问的请求源方法
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setMaxAge(3600L);
//        // 返回CORS配置
//        return corsConfiguration;
//    }
//
//    /**
//     * 配置跨域过滤器
//     * @return
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // 注册跨域配置
//        source.registerCorsConfiguration("/**", corsConfig());
//        return new CorsFilter(source);
//    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        System.out.println("CorsConfig:addInterceptors......");

        // 可以添加多个拦截器，一般只添加一个
        // addPathPatterns("/**") 表示对所有请求都拦截
        // .excludePathPatterns("/base/index") 表示排除对/base/index请求的拦截
        // 多个拦截器可以设置order顺序，值越小，preHandle越先执行，postHandle和afterCompletion越后执行
        // order默认的值是0，如果只添加一个拦截器，可以不显示设置order的值
        registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**");
        // registry.addInterceptor(userPermissionInterceptorAdapter).addPathPatterns("/**")
        // .excludePathPatterns("/base/index").order(1);
    }

}
