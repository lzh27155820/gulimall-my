package com.liu.xyz.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.to.SkuReductionTo;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-30 00:34:59
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveLadderAndReductionAndPrice(SkuReductionTo skuReductionTo);
}

