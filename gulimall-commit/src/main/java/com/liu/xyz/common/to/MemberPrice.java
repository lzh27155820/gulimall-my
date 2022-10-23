package com.liu.xyz.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * create liu 2022-10-14
 */
@Data
public class MemberPrice {
    private Long id;
    private String name;
    private BigDecimal price;
}
