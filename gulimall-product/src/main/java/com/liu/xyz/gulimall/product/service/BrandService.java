package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface BrandService extends IService<BrandEntity> {
    /**
     * 分页查询 带条件
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     *  修改 及冗余字段
     * @param brand
     */
    void detailedUpdate(BrandEntity brand);
}

