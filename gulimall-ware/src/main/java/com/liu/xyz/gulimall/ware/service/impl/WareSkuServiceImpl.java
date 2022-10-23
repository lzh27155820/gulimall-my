package com.liu.xyz.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.to.SkuHasStockVo;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.ware.dao.WareSkuDao;
import com.liu.xyz.gulimall.ware.entity.WareSkuEntity;
import com.liu.xyz.gulimall.ware.service.WareSkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();

        String wareId =(String) params.get("wareId");
        String skuId =(String) params.get("skuId");

        if(!StringUtils.isEmpty(wareId)){
            wrapper.eq("ware_id",wareId);

        }
        if(!StringUtils.isEmpty(skuId)){
            wrapper.eq("sku_id",Long.parseLong(skuId));
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuHasStockVo> getHastStock(List<Long> skuIds) {


        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
                    SkuHasStockVo stockVo = new SkuHasStockVo();
                    //获取所有sku库存总量
                    Long stock = baseMapper.selectSum(skuId);

                    stockVo.setSkuId(skuId);
                    stockVo.setHastStock(stock == null ? false : stock > 0);
                    return stockVo;
                }
        ).collect(Collectors.toList());
        return collect;

    }

}