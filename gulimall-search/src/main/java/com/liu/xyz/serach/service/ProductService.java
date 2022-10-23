package com.liu.xyz.serach.service;

import com.liu.xyz.common.to.es.SkuESModel;

import java.util.List;

/**
 * create liu 2022-10-15
 */
public interface ProductService {
    boolean upDateEs(List<SkuESModel> esModelList);
}
