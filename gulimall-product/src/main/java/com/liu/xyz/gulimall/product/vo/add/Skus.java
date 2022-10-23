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
public class Skus {

    private List<Attr> attr;//基本属性
    private String skuName;//销售商品名称 如: "OPPOK10x  篮色 8+256"
    private BigDecimal price;//商品价格
    private String skuTitle;// 商品标题
    private String skuSubtitle;//副标题
    private List<Images> images;//sku商品图片
    private List<String> descar;// 销售属性值
    private int fullCount;// 满多少件打折
    private BigDecimal discount;//打多少折
    private int countStatus;//是否可叠加优惠
    private BigDecimal fullPrice;//满多少元
    private BigDecimal reducePrice;//满减多少元
    private int priceStatus;//是否可叠加优惠
    private List<MemberPrice> memberPrice;//会员价格


}