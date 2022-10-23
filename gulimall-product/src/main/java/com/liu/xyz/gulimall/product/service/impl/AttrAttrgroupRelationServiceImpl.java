package com.liu.xyz.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.liu.xyz.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.liu.xyz.gulimall.product.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AttrAttrgroupRelationEntity getAttWithGroupByAttrId(Long attrId) {

        QueryWrapper<AttrAttrgroupRelationEntity> wrapper =
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId);
        AttrAttrgroupRelationEntity entity = this.getOne(wrapper);
        return entity;
    }

    @Override
    public void updateAttrGroupId(Long attrId, Long attrGroupId) {

        AttrAttrgroupRelationEntity one = this.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));


        AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();

        UpdateWrapper<AttrAttrgroupRelationEntity> wrapper = new UpdateWrapper<>();
        relation.setAttrId(attrId);
        relation.setAttrGroupId(attrGroupId);
        wrapper.eq("attr_id",attrId);
        if(one==null){
            this.save(relation);
        }else {
            this.update(relation,wrapper);
        }
    }



}