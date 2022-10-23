package com.liu.xyz.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.to.BoundsTo;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.coupon.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-30 00:34:59
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 远程调用发布商品 保存 购买获取金币 成长值
     * @param boundsTo
     */
    void saveBounds(BoundsTo boundsTo);
}

