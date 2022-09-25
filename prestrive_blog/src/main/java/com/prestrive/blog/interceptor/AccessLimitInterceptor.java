package com.prestrive.blog.interceptor;

import cn.hutool.json.JSONObject;

import com.prestrive.blog.annotation.AccessLimit;
import com.prestrive.blog.common.lang.Result;
import com.prestrive.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * HandlerInterceptor：处理器拦截器
 * 控制接口访问频率，处理方法体上有accesslimit注解的逻辑
 *
 * @author fanfanli
 * @date 2021/8/11
 */
@Component
public class AccessLimitInterceptor  implements HandlerInterceptor  {
    @Autowired
    RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AccessLimitInterceptor:preHandle.......");
        if (handler instanceof HandlerMethod) {
            //获取方法处理器
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法体上的Annotation注解
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            //方法上没有访问控制的注解，直接通过
            if (accessLimit == null) {
                return true;
            }
            //从注解里获取参数
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            //获取ip
            String ip = request.getHeader("x-forwarded-for");
            //获取请求方法
            String method = request.getMethod();
            //获取请求URI
            String requestURI = request.getRequestURI();
            //构建唯一的redis键
            String redisKey = ip + ":" + method + ":" + requestURI;
            //从redis 取出访问次数
            Integer count = (Integer) redisService.getObjectByValue(redisKey);

            if (count == null) {
                //在规定周期内第一次访问，存入redis
                redisService.saveObjectToValue(redisKey, 1);
                //设置过期时间（限制周期：seconds）
                redisService.expire(redisKey, seconds);
            } else {
                //在这个周期内 访问次数 大于 最大访问次数
                if (count >= maxCount) {
                    //超出访问限制次数
                    response.setContentType("application/json;charset=utf-8");
                    //返回fail
                    PrintWriter out = response.getWriter();//获取响应编写器
                    Result result = Result.fail(403, accessLimit.msg(),null);
                    out.write(String.valueOf(new JSONObject(result)));//将result 转换为json 写入 响应编写器
                    out.flush();//刷新响应编写器
                    out.close();//关闭响应编写器
                    return false;
                } else {
                    //没超出访问限制次数，+1
                    redisService.incrementByKey(redisKey, 1);
                }
            }
        }
        return true;
    }
}
