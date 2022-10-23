package com.liu.xyz.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * create liu 2022-10-14
 */
@Data
public class BoundsTo {
    private BigDecimal buyBounds;//购买获得金币
    private BigDecimal growBounds;//成长值
    private Long spuId;
}
