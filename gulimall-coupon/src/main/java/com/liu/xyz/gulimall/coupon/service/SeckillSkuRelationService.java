package com.liu.xyz.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-30 00:34:59
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
