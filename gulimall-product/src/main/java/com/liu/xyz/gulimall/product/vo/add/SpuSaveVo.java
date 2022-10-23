/**
 * Copyright 2019 bejson.com
 */
package com.liu.xyz.gulimall.product.vo.add;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2019-11-26 10:50:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuSaveVo {

    private String spuName; //商品名称 就有唯一形
    private String spuDescription;//商品介绍
    private Long catalogId;//商品分类id
    private Long brandId;//品牌id
    private BigDecimal weight;//重量
    private int publishStatus;//
    private List<String> decript;//商品描述
    private List<String> images;//商品图片
    private Bounds bounds;//
    private List<BaseAttrs> baseAttrs;//基础属性
    private List<Skus> skus;//销售属性



}