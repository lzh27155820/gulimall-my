package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.CategoryBrandRelationEntity;
import com.liu.xyz.gulimall.product.vo.CategoryBrandVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过 brandId 修改 brandName
     * @param brandId
     * @param brandName
     */
    void updateByIdToName(Long brandId, String brandName);

    /**
     *  根据brandId 查询 所有的 CategoryBrandRelationEntity
     * @param brandId
     * @return
     */
    List<CategoryBrandRelationEntity> selectByBrandId(Long brandId);

    /**
     *  保存 保留冗余字段
     * @param categoryBrandRelation
     */
    void saveDetailed(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 获取分类关联的品牌
     *      根据 catId 获取它所有的品牌
     * @param catId
     * @return
     */
    List<CategoryBrandVo> brandsList(Long catId);
}

