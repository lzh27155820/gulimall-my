package com.liu.xyz.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * create liu 2022-10-14
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private Integer fullCount;// 满多少件打折
    private BigDecimal discount;//打多少折
    private Integer countStatus;//是否可叠加优惠
    private BigDecimal fullPrice;//满多少元
    private BigDecimal reducePrice;//满减多少元
    private Integer priceStatus;//是否可叠加优惠
    private List<MemberPrice> memberPrice;//会员价格
}

