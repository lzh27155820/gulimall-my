package com.liu.xyz.gulimall.product.controller;

import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.R;
import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.entity.AttrGroupEntity;
import com.liu.xyz.gulimall.product.service.AttrGroupService;
import com.liu.xyz.gulimall.product.service.CategoryService;
import com.liu.xyz.gulimall.product.vo.AttrGroupVo;
import com.liu.xyz.gulimall.product.vo.AttrGroupWithAttrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 属性分组
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;

    /*
    /product/attrgroup/
    17、获取分类下所有分组&关联属性 在发布商品里
     */
    @RequestMapping("{catelogId}/withattr")
    public R withAttr(@PathVariable("catelogId") Long catelogId){

       List<AttrGroupWithAttrs> attrGroupWithAttrs =attrGroupService.getGroupWithAttr(catelogId);
        return R.ok().put("data",attrGroupWithAttrs);
    }

    /*
    12、删除属性与分组的关联关系
     *http://localhost:88/api/product/attrgroup/attr/relation/delete
     * attrId: 14, attrGroupId: 3
     */
    @RequestMapping("attr/relation/delete")
    public R attrRelationDelete(@RequestBody List<AttrGroupVo> attrGroupVos){

        attrGroupService.attrRelationDelete(attrGroupVos);

        return R.ok();
    }


    /*
    11、添加属性与分组关联关系
    http://localhost:88/api/product/attrgroup/attr/relation
    attrId: 14, attrGroupId: 3
     */

    @RequestMapping("/attr/relation")
    public R attrRelation(@RequestBody List<AttrGroupVo> attrGroupVo){


        attrGroupService.saveAttrRelation(attrGroupVo);
        return R.ok();
    }
    /*
    13、获取属性分组没有关联的其他属性
    http://localhost:88/api/product/attrgroup/3/noattr/relation?t=1665668606444&page=1&limit=10&key=
     */
    @RequestMapping("/{attrgroupId}/noattr/relation")
    public R noattrRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrgroupId){

        PageUtils page   =attrGroupService.noattrRelation(params,attrgroupId);

        return R.ok().put("page",page);
    }

    /*
    10、获取属性分组的关联的所有属性
    /product/attrgroup/
     */
    @RequestMapping("{attrgroupId}/attr/relation")
    public R attrgroupIdAttrRelation(@PathVariable("attrgroupId") Long attrgroupId){

       List<AttrEntity> list =attrGroupService.attrgroupIdAttrRelation(attrgroupId);
        return R.ok().put("data",list);
    }
    /**
     * 03、获取分类属性分组
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId ){
        PageUtils page = attrGroupService.queryPage(params,catelogId);

        return R.ok().put("page", page);
    }
    /*
    http://localhost:88/api/product/attrgroup/list/0?t=1665626678148&page=1&limit=10&key=
     */

    /**
     * 04、获取属性分组详情
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] threePath = categoryService.getThreePath(attrGroup.getCatelogId());
        attrGroup.setCatelogPath(threePath);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
