package com.liu.xyz.serach.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.liu.xyz.common.to.es.SkuESModel;
import com.liu.xyz.serach.config.EsConstant;
import com.liu.xyz.serach.service.ProductService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * create liu 2022-10-15
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean upDateEs(List<SkuESModel> esModelList) {

        BulkRequest bulkRequest = new BulkRequest();

        for (SkuESModel esModel:esModelList){
            //
            IndexRequest request = new IndexRequest(EsConstant.PRODUCT_INDEX);
            //
            request.id(esModel.getSkuId().toString());
            String s = JSON.toJSONString(esModel);
            request.source(s, XContentType.JSON);
            bulkRequest.add(request);
        }
        try {
            //批量增加
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT.toBuilder().build());
            //判断处理是否成功
            int status = response.status().getStatus();
            if(status==200){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
