package com.liu.xyz.serach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * create liu 2022-10-15
 */


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SearchApplication
{
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
}
