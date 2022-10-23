package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.liu.xyz.gulimall.product.vo.add.Attr;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  保存 商品的销售属性值
     * @param skuId
     * @param attrList
     */
    void saveAttr(Long skuId, List<Attr> attrList);
}

