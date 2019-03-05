package com.wjc.ccf.test;

import org.apache.shiro.util.Assert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.*;

public class JavaTest {
    public static void main(String []args)throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
        System.out.println(System.nanoTime());

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:com/wjc/ccf/**");
        Assert.notNull(resources);
        for (Resource resource : resources) {
            System.out.println(resource.getDescription());
        }
        Resource resource = new ClassPathResource("application.properties");
        EncodedResource encodedResource = new EncodedResource(resource, "gbk");
        String content = FileCopyUtils.copyToString(encodedResource.getReader());
        System.out.println(content);

    }

}
