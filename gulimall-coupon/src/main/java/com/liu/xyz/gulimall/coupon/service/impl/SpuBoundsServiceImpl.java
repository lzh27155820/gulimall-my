package com.liu.xyz.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.to.BoundsTo;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.coupon.dao.SpuBoundsDao;
import com.liu.xyz.gulimall.coupon.entity.SpuBoundsEntity;
import com.liu.xyz.gulimall.coupon.service.SpuBoundsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("spuBoundsService")
public class SpuBoundsServiceImpl extends ServiceImpl<SpuBoundsDao, SpuBoundsEntity> implements SpuBoundsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuBoundsEntity> page = this.page(
                new Query<SpuBoundsEntity>().getPage(params),
                new QueryWrapper<SpuBoundsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBounds(BoundsTo boundsTo) {
        SpuBoundsEntity spuBounds = new SpuBoundsEntity();
        spuBounds.setSpuId(boundsTo.getSpuId());
        spuBounds.setBuyBounds(boundsTo.getBuyBounds());
        spuBounds.setGrowBounds(boundsTo.getGrowBounds());
        this.save(spuBounds);
    }

}