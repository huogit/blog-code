package com.prestrive.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.mybatis.logging.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * 根据IP地址获取详细的地域信息
 */
@Slf4j
@Component
public class IpAddressUtils {



    private static DbSearcher searcher;
    private static Method method;

    /**
     * 在服务启动时加载 ip2region.db 到内存中
     * 解决打包jar后找不到 ip2region.db 的问题
     */
    @PostConstruct //spring容器初始化的时候执行该方法
    private void initIp2regionResource() {
        System.out.println("IpAddressUtils:initIp2regionResource");
        try {
            //加载ip2region.db 到 输入流
            InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();

            //将 ip2region.db 转为 ByteArray
            byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
            // 实例化 DbConfig
            DbConfig dbConfig = new DbConfig();
            // 创建 searcher 对象
            searcher = new DbSearcher(dbConfig, dbBinStr);
            //二进制方式初始化 DBSearcher，需要使用基于内存的查找算法 memorySearch
            method = searcher.getClass().getMethod("memorySearch", String.class);
        } catch (Exception e) {
            log.error("initIp2regionResource exception:", e);
        }
    }

    /**
     * 根据ip从 ip2region.db 中获取地理位置
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        if (ip == null || !Util.isIpAddress(ip)) {
            log.error("Error: Invalid ip address"); // Invalid 无效
            return null;
        }
        try {
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            String ipInfo = dataBlock.getRegion();
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            log.error("getCityInfo exception:", e);
        }
        return null;
    }


//    @SuppressWarnings("all")
//    public static String getCityInfo(String ip) {
//        //获取 ip db 文件路径
//        String dbPath = IpAddressUtils.class.getResource("/ipdb/ip2region.db").getPath();
//        File file = new File(dbPath);
//
//        if (!file.exists()) {
//            log.info("地址库文件不存在,进行其他处理");
//            String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
//            dbPath = tmpDir + File.separator + "ip2region.db";
//            log.info("临时文件路径:{}", dbPath);
//            file = new File(dbPath);
//            if (!file.exists() || (System.currentTimeMillis() - file.lastModified() > 86400000L)) {
//                log.info("文件不存在或者文件存在时间超过1天进入...");
//                try {
//                    InputStream inputStream = new ClassPathResource("ip2region/ip2region.db").getInputStream();
//                    IOUtils.copy(inputStream, new FileOutputStream(file));
//                } catch (IOException exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }
//
//        //查询算法
//        int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
//        try {
//            DbConfig config = new DbConfig();
//            DbSearcher searcher = new DbSearcher(config, dbPath);
//            //define the method
//            Method method = null;
//            switch (algorithm) {
//                case DbSearcher.BTREE_ALGORITHM:
//                    method = searcher.getClass().getMethod("btreeSearch", String.class);
//                    break;
//                case DbSearcher.BINARY_ALGORITHM:
//                    method = searcher.getClass().getMethod("binarySearch", String.class);
//                    break;
//                case DbSearcher.MEMORY_ALGORITYM:
//                    method = searcher.getClass().getMethod("memorySearch", String.class);
//                    break;
//            }
//            DataBlock dataBlock = null;
//            if (Util.isIpAddress(ip) == false) {
//                log.error("Error: Invalid ip address");
//            }
//            dataBlock = (DataBlock) method.invoke(searcher, ip);
//            return dataBlock.getRegion();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}


