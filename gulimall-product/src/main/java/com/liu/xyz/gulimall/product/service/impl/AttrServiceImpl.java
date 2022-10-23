package com.liu.xyz.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.myutils.ProductConstant;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.product.dao.AttrDao;
import com.liu.xyz.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.entity.AttrGroupEntity;
import com.liu.xyz.gulimall.product.entity.CategoryEntity;
import com.liu.xyz.gulimall.product.service.AttrAttrgroupRelationService;
import com.liu.xyz.gulimall.product.service.AttrGroupService;
import com.liu.xyz.gulimall.product.service.AttrService;
import com.liu.xyz.gulimall.product.service.CategoryService;
import com.liu.xyz.gulimall.product.vo.AttrRespVo;
import com.liu.xyz.gulimall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Transactional
    @Override
    public PageUtils queryPage(Map<String, Object> params,Long catelogId,String attrType) {


        String key =(String) params.get("key");
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        //1 为基础属性 0 为销售属性
        wrapper.eq("attr_type", "base".equalsIgnoreCase(attrType)?
                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                :ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        //2. 确定 catelog_id
        if(catelogId!=0){
            wrapper.eq("catelog_id",catelogId);
        }
        //3. 判断key有值不
        if(!StringUtils.isEmpty(key)){
            wrapper.and(obj->{
              obj.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        //4.分页查询
        IPage<AttrEntity> page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    wrapper
        );
            List<AttrEntity> records = page.getRecords();

            List<AttrRespVo> collect = records.stream().map(att -> {
                AttrRespVo attrVo = new AttrRespVo();
                //5.对拷数值
                BeanUtils.copyProperties(att, attrVo);
                //6.封装 catelogName
                CategoryEntity categoryEntity = categoryService.getById(att.getCatelogId());
                if(categoryEntity!=null){
                    attrVo.setCatelogName(categoryEntity.getName());
                }
                //7. 封装 groupName
                AttrAttrgroupRelationEntity attr = attrAttrgroupRelationService.getAttWithGroupByAttrId(att.getAttrId());
                if(attr!=null){
                    AttrGroupEntity attrGroup = attrGroupService.getById(attr.getAttrGroupId());
                    //只有基本属性需要主题信息，销售属性不用
                    if(attrGroup!=null&&"base".equalsIgnoreCase(attrType)){
                        attrVo.setGroupName(attrGroup.getAttrGroupName());
                    }
                }
                return attrVo;
            }).collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);

         pageUtils.setList(collect);
        return pageUtils;

    }

    @Transactional
    @Override
    public void saveDetailed(AttrVo attrVo) {
        //1.保存基本信息
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.save(attrEntity);
        //2.保存关联关系pms_attr_attrgroup_relation

        if(attrVo.getAttrGroupId()!=null&&attrVo.getAttrType()==
                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){

            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationService.save(relationEntity);
        }

    }

    @Transactional
    @Override
    public void updateDetailed(AttrVo attrVo) {
        //1.修改当前属性
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.updateById(attrEntity);
        //2.修改对应的关联关系

        if(attrVo.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            attrAttrgroupRelationService.updateAttrGroupId(attrVo.getAttrId(),attrVo.getAttrGroupId());
        }
    }

    @Transactional
    @Override
    public AttrRespVo getByIdDetailed(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        //1.获取当前的属性值
        AttrEntity attrEntity = this.getById(attrId);

        BeanUtils.copyProperties(attrEntity,respVo);
        //2.判断是基本属性
        if(attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //2.1.获取关联的属性
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq
                    ("attr_id", attrEntity.getAttrId()));
            respVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
            //2.1.2 设置GroupName
            AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrAttrgroupRelationEntity.getAttrGroupId());
            if(attrGroupEntity!=null){
                respVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        //4. 设置 catelogPath
        Long[] threePath = categoryService.getThreePath(attrEntity.getCatelogId());
        if(threePath!=null){
            respVo.setCatelogPath(threePath);
        }
        //5.
        CategoryEntity category = categoryService.getById(attrEntity.getCatelogId());
        if(category!=null){
            respVo.setCatelogName(category.getName());
        }

        return respVo;
    }

    @Override
    public List<Long> getSearchAttrIds(List<Long> attrIds) {
        return baseMapper.getSearchAttrIds(attrIds);
    }

}