package com.liu.xyz.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.vo.AttrRespVo;
import com.liu.xyz.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params,Long catelogId,String attrType);

    /**
     *  保存 Attr 及 属性 和属性分组 关联与 关系 pms_attr_attrgroup_relation
     * @param attrVo
     */
    void saveDetailed(AttrVo attrVo);

    /**
     * 修改 及关联关系
     * @param attrVo
     */
    void updateDetailed(AttrVo attrVo);

    AttrRespVo getByIdDetailed(Long attrId);

    /**
     * 查询 可检索的 attrid
     * @param attrIds
     * @return
     */
    List<Long> getSearchAttrIds(List<Long> attrIds);
}

