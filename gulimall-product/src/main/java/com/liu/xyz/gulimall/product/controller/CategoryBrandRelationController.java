package com.liu.xyz.gulimall.product.controller;

import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.R;
import com.liu.xyz.gulimall.product.entity.CategoryBrandRelationEntity;
import com.liu.xyz.gulimall.product.service.CategoryBrandRelationService;
import com.liu.xyz.gulimall.product.vo.CategoryBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 品牌分类关联
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /*
    14、获取分类关联的品牌
     * product/categorybrandrelation/brands/list
     */
    @RequestMapping("/brands/list")
    public R brandsList(@RequestParam("catId") Long catId){

       List<CategoryBrandVo> list=categoryBrandRelationService.brandsList(catId);
        return R.ok().put("data",list);
    }
    /*
     * 15、获取品牌关联的分类
     * /product/categorybrandrelation/catelog/list
     * http://localhost:88/api/product/categorybrandrelation/catelog/list?t=1665584627465&brandId=1
     */

    @RequestMapping("catelog/list")
    public R catelogList(@RequestParam("brandId") Long brandId ){

        List<CategoryBrandRelationEntity> list =categoryBrandRelationService.selectByBrandId(brandId);
        return R.ok().put("data",list);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /*
    16、新增品牌与分类关联关系
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetailed(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
