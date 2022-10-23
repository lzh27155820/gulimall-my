package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.entity.AttrGroupEntity;
import com.liu.xyz.gulimall.product.vo.AttrGroupVo;
import com.liu.xyz.gulimall.product.vo.AttrGroupWithAttrs;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {
    /**
     *  根据 分类列表 catelogId 查询 对应的 分组
     *      解释：手机 ，每个手机都有对应的参数
     * @param params
     * @param catelogId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params,Long catelogId);

    /**
     * 获取属性分组的关联的所有属性
     * @param attrgroupId
     * @return
     */
    List<AttrEntity> attrgroupIdAttrRelation(Long attrgroupId);

    /**
     * 获取属性分组没有关联的其他属性
     * @param params
     * @param attrgroupId
     * @return
     */
    PageUtils noattrRelation(Map<String, Object> params, Long attrgroupId);

    /**
     *  保存 关联关系
     */
    void saveAttrRelation(List<AttrGroupVo> attrGroupVo);

    /**
     *  删除对应的关联关系
     * @param attrGroupVos
     */
    void attrRelationDelete(List<AttrGroupVo> attrGroupVos);

    /**
     *  查询商品分类下所有的 品牌
     * @param catelogId
     * @return
     */
    List<AttrGroupWithAttrs> getGroupWithAttr(Long catelogId);
}

