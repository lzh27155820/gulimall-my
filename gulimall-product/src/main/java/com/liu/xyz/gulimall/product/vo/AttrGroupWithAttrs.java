package com.liu.xyz.gulimall.product.vo;

import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

/**
 * create liu 2022-10-14
 */
@Data
public class AttrGroupWithAttrs extends AttrGroupEntity {

    private List<AttrEntity> attrs;
}
