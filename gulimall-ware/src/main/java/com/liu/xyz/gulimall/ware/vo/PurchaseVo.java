package com.liu.xyz.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * create liu 2022-10-22
 */
@Data
public class PurchaseVo {

    private Long purchaseId;
    private List<Long> items;
}
