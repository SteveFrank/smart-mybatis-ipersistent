package com.smart.ipersistent.io;

import java.io.InputStream;

/**
 * 第一步，获取配置信息
 * @author frankq
 * @date 2021/11/8
 */
public class Resources {

    /**
     * 根据配置文件路径，将配置文件加载成字节流输入，存入到内存中
     */
    public static InputStream getResourceAsStream(String path) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("配置文件无法加载");
        }
        return inputStream;
    }

}
