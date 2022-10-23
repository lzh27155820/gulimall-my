package com.liu.xyz.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * create liu 2022-10-15
 */
@Data
public class SkuESModel {

    private Long skuId;

    private String skuTitle;//每件商品名称

    private BigDecimal skuPrice;//每件商品价格

    private String skuImg;//默认图片

    private Long saleCount;

    private Boolean hastStock;

    private Long hotScore;// 查看 库存 hastStock hotScore

    private Long brandId;

    private Long catalogId;

    private String brandName;//品牌名

    private String brandImg; //品牌图

    private String catalogName; //分类名

    private List<Attrs> attrsList; //商品属性检索

    @Data
    public static  class Attrs{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}
