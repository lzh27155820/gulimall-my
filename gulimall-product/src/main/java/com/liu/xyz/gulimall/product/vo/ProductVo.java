package com.liu.xyz.gulimall.product.vo;

import lombok.Data;

/**
 * create liu 2022-10-22
 */
@Data
public class ProductVo {
 /*
        "attrId": 7,
         "attrName": "入网型号",
         "attrValue": "LIO-AL00",
          "quickShow": 1
    */
    private Long attrId;
    private String attrName;
    private String attrValue;
    private Integer quickShow;
}
