package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.ProductAttrValueEntity;
import com.liu.xyz.gulimall.product.vo.ProductVo;
import com.liu.xyz.gulimall.product.vo.add.BaseAttrs;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *发布商品 保存基础属性值
     * @param baseAttrs
     * @param spuId
     */
    void saveBaseAttrs(List<BaseAttrs> baseAttrs, Long spuId);

    /**
     *  spu的规格维护
     *
     * @param spuId 根据spuId获取
     * @return 返回spu的基本基本属性的参数值
     */
    List<ProductAttrValueEntity> BaseListforspuSpuId( Long spuId);

    /**
     *
     * @param spuId  根据spuId修改
     * @param list 修改内容
     */
    void updateToSpuId(Long spuId, List<ProductVo> list);
}

