package com.liu.xyz.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.product.dao.CategoryBrandRelationDao;
import com.liu.xyz.gulimall.product.entity.BrandEntity;
import com.liu.xyz.gulimall.product.entity.CategoryBrandRelationEntity;
import com.liu.xyz.gulimall.product.entity.CategoryEntity;
import com.liu.xyz.gulimall.product.service.BrandService;
import com.liu.xyz.gulimall.product.service.CategoryBrandRelationService;
import com.liu.xyz.gulimall.product.service.CategoryService;
import com.liu.xyz.gulimall.product.vo.CategoryBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    @Lazy //bean 依赖注入时解决循环注入
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateByIdToName(Long brandId, String brandName) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(brandName);
        this.update(entity,new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));

    }

    @Override
    public List<CategoryBrandRelationEntity> selectByBrandId(Long brandId) {

        List<CategoryBrandRelationEntity> list = this.baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));


        return list;
    }

    @Override
    public void saveDetailed(CategoryBrandRelationEntity categoryBrandRelation) {

        BrandEntity brandEntity = brandService.getById(categoryBrandRelation.getBrandId());
        CategoryEntity categoryEntity = categoryService.getById(categoryBrandRelation.getCatelogId());

        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public List<CategoryBrandVo> brandsList(Long catId) {

        List<CategoryBrandRelationEntity> categoryBrandRelationEntities = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));

        List<CategoryBrandVo> collect = categoryBrandRelationEntities.stream().map(obj -> {
            CategoryBrandVo categoryBrandVo = new CategoryBrandVo();
            categoryBrandVo.setBrandId(obj.getBrandId());
            categoryBrandVo.setBrandName(obj.getBrandName());
            return categoryBrandVo;
        }).collect(Collectors.toList());

        return collect;
    }

}