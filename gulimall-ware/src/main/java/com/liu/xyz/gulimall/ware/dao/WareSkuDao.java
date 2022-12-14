package com.liu.xyz.gulimall.ware.dao;

import com.liu.xyz.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * εεεΊε­
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-30 00:21:36
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long selectSum(Long skuId);
}
