package com.liu.xyz.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * create liu 2022-10-12
 */
@EnableTransactionManagement //开启事物
@Configuration
public class MybatisConfig {
    /*

     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
        //如果请求页面大于最大页 就返回首页
        interceptor.setOverflow(true);

        return interceptor;
    }
}
