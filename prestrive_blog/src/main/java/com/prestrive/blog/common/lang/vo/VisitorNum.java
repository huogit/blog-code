package com.prestrive.blog.common.lang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 传给前端的PV和UV的视图对象
 * PV(访问量)：即Page View, 具体是指网站的是页面浏览量或者点击量；
 * UV(独立访客)：即Unique Visitor，访问您网站的一台电脑客户端为一个访客。
 * 根据IP地址来区分访客数，在一段时间内重复访问，也算是一个UV；UV价值=销售额/访客数
 * @author fanfanli
 * @date  2021/4/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VisitorNum  implements Serializable {
    private static final long serialVersionUID = 1L;
    int uv;
    int pv;
}