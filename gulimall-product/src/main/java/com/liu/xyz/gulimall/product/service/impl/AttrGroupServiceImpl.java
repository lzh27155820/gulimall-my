package com.liu.xyz.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.myutils.ProductConstant;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.liu.xyz.gulimall.product.dao.AttrGroupDao;
import com.liu.xyz.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.entity.AttrGroupEntity;
import com.liu.xyz.gulimall.product.service.*;
import com.liu.xyz.gulimall.product.vo.AttrGroupVo;
import com.liu.xyz.gulimall.product.vo.AttrGroupWithAttrs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    @Lazy
    private AttrService attrService;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params,Long catelogId) {

        String key =(String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("attr_group_id",key).or().like("attr_group_name",key);
        }
        //如果是零就返回全部
        if(catelogId==0){
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }else {

            Long[]  longs=categoryService.getThreePath(catelogId);
            wrapper.eq("catelog_id",catelogId);
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            List<AttrGroupEntity> records = page.getRecords();
            List<AttrGroupEntity> list = records.stream().map(obj -> {
                obj.setCatelogPath(longs);
                return obj;
            }).collect(Collectors.toList());

            page.setRecords(list);
            return new PageUtils(page);
        }


    }

    @Override
    public List<AttrEntity> attrgroupIdAttrRelation(Long attrgroupId) {

        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = attrAttrgroupRelationService.
                list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));

        if(attrAttrgroupRelationEntityList!=null){

            List<Long> list = attrAttrgroupRelationEntityList.stream().map(obj -> obj.getAttrId()).collect(Collectors.toList());

            QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
            wrapper.in("attr_id",list);
            List<AttrEntity> attrEntityList = attrService.list(wrapper);
            return attrEntityList;
        }

        return null;
    }

    @Override
    public PageUtils noattrRelation(Map<String, Object> params, Long attrgroupId) {
        //1.商品分类 下的子分类 获取当前属性分组 比如手机有 主体，基本信息 都在手机里
        AttrGroupEntity attrGroup = this.getById(attrgroupId);
        Long catelogId = attrGroup.getCatelogId();
        List<AttrGroupEntity> attrGroupEntityList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //1.1 获取 AttrGroupEntity 的所有attr_group_id
        List<Long> attrGroupIds = attrGroupEntityList.stream().map(obj -> obj.getAttrGroupId()).collect(Collectors.toList());
        //2. 根据 attr_group_ids 获取所有 该属性关联的
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList= attrAttrgroupRelationService.list(new
                QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id",attrGroupIds));
        //2.1 获取对应的AttrId
        List<Long> attrIds = attrAttrgroupRelationEntityList.stream().map(obj -> obj.getAttrId()).collect(Collectors.toList());
        //3.确定查询的是基本属性 和 对应的 商品分类id是确定的
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())
                .eq("catelog_id", catelogId);
        //3.1 排除对应已关联属性
        if(attrIds!=null&&attrIds.size()>0){
            wrapper.notIn("attr_id",attrIds);
        }

        //4.获取对应key
        String key =(String) params.get("key");

        if(!StringUtils.isEmpty(key)){
            wrapper.and(obj->{
               obj.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = attrService.page(new Query<AttrEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void saveAttrRelation(List<AttrGroupVo> attrGroupVo) {

        List<AttrAttrgroupRelationEntity> collect = attrGroupVo.stream().map(obj -> {
            AttrAttrgroupRelationEntity entity =
                    new AttrAttrgroupRelationEntity();

            entity.setAttrId(obj.getAttrId());
            entity.setAttrGroupId(obj.getAttrGroupId());
            return entity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationService.saveBatch(collect);
    }

    @Override
    public void attrRelationDelete(List<AttrGroupVo> attrGroupVos) {
        List<Long> attrIds = attrGroupVos.stream().map(obj -> obj.getAttrId()).collect(Collectors.toList());
        List<Long> attrGroupIds = attrGroupVos.stream().map(obj -> obj.getAttrGroupId()).collect(Collectors.toList());


        attrAttrgroupRelationDao.deleteRelation(attrIds,attrGroupIds);
    }

    @Override
    public List<AttrGroupWithAttrs> getGroupWithAttr(Long catelogId) {
        //1.查询所有的商品分类下的属性分组
        List<AttrGroupEntity> attrGroupEntityList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        if(attrGroupEntityList!=null&&attrGroupEntityList.size()>0) {
            List<AttrGroupWithAttrs> groupWithAttrsList = attrGroupEntityList.stream().map(obj -> {

                AttrGroupWithAttrs withAttrs = new AttrGroupWithAttrs();

                //2.对拷
                BeanUtils.copyProperties(obj, withAttrs);
                //3. 查询对应的属性和属性分组 设置属性 List<AttrEntity> attrs
                Long attrGroupId = obj.getAttrGroupId();
                List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = attrAttrgroupRelationService.list(
                        new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
                if (attrAttrgroupRelationEntityList != null) {

                    List<Long> attrIds = attrAttrgroupRelationEntityList.stream().map(oj -> {
                        return oj.getAttrId();
                    }).collect(Collectors.toList());
                    if (attrIds != null) {
                        List<AttrEntity> list = attrService.listByIds(attrIds);
                        withAttrs.setAttrs(list);
                    }
                }

                return withAttrs;
            }).collect(Collectors.toList());

            return groupWithAttrsList;
        }else {
            return null;
        }


    }


}