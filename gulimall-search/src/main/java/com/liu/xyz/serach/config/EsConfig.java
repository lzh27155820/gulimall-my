package com.liu.xyz.serach.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create liu 2022-10-23
 */
@Configuration
public class EsConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
       return new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.229.151",9200,"http")));
    }
}
