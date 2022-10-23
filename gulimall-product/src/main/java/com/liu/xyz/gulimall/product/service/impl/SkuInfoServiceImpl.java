package com.liu.xyz.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.product.dao.SkuInfoDao;
import com.liu.xyz.gulimall.product.entity.SkuInfoEntity;
import com.liu.xyz.gulimall.product.service.SkuInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //lt （less than）               小于
        //le （less than or equal to）   小于等于
        //eq （equal to）                等于
        //ne （not equal to）            不等于
        //ge （greater than or equal to）大于等于
        //gt （greater than）            大于
        //&page=1&limit=10&key=&catelogId=0&brandId=0&min=0&max=0
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String)params.get("key");
        String max =(String) params.get("max");
        String min =(String) params.get("min");
        if(!StringUtils.isEmpty(key)){
            wrapper.like("spu_name",key);
        }
        if(min!=null&&Integer.parseInt(min)>0){
            wrapper.ge("price",new BigDecimal(Integer.parseInt(min)));
        }
        if(max!=null&&Integer.parseInt(max)>0){
            wrapper.le("price",new BigDecimal(Integer.parseInt(max)));
        }


        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}