package com.liu.xyz.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.ware.dao.PurchaseDetailDao;
import com.liu.xyz.gulimall.ware.entity.PurchaseDetailEntity;
import com.liu.xyz.gulimall.ware.service.PurchaseDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String)params.get("key");
        String status =(String) params.get("status");
        String wareId =(String) params.get("wareId");

        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();


        if(!StringUtils.isEmpty(key)){
            wrapper.eq("sku_id",Long.parseLong(key));
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",Long.parseLong(status));
        }

        if(!StringUtils.isEmpty(wareId)){
            wrapper.eq("ware_id",Long.parseLong(wareId));
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}