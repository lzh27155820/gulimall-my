package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 以树型结构 返回分类 和子分类
     * @return
     */
    List<CategoryEntity> listWithTree();

    /**
     *  根据catelogId 查询父类多层路径id
     * @param catelogId
     * @return
     */
    Long[] getThreePath(Long catelogId);
}

