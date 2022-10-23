package com.liu.xyz.gulimall.product.controller;

import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.R;
import com.liu.xyz.gulimall.product.entity.ProductAttrValueEntity;
import com.liu.xyz.gulimall.product.service.AttrService;
import com.liu.xyz.gulimall.product.service.ProductAttrValueService;
import com.liu.xyz.gulimall.product.vo.AttrRespVo;
import com.liu.xyz.gulimall.product.vo.AttrVo;
import com.liu.xyz.gulimall.product.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 商品属性
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-29 22:18:42
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /*
    23、修改商品规格
    /product/attr/update/{spuId}
     */
    @RequestMapping("/update/{spuId}")
    public R updateToSpuId(@PathVariable("spuId") Long spuId,@RequestBody List<ProductVo> list){


        System.out.println(list);
        productAttrValueService.updateToSpuId(spuId,list);
        return R.ok();
    }
    /*
    22、获取spu规格 GET
/product/attr/base/listforspu/{spuId}
     */
    @RequestMapping("/base/listforspu/{spuId}")
    public R BaseListforspuSpuId(@PathVariable("spuId") Long spuId){


        List<ProductAttrValueEntity> list=productAttrValueService.BaseListforspuSpuId(  spuId);
        return R.ok().put("data",list);
    }
    /*
        05、获取分类规格参数
     * 列表http://localhost:88/api/product/attr/base/list/0
     */
    @RequestMapping("{attrType}/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId,
                  @PathVariable("attrType") String attrType){
        PageUtils page = attrService.queryPage(params,catelogId,attrType);

        return R.ok().put("page", page);
    }


    /**
     * 07、查询属性详情
     * /product/attr/info/{attrId}
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		//AttrEntity attr = attrService.getById(attrId);
        AttrRespVo attr = attrService.getByIdDetailed(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存06、保存属性【规格参数，销售属性】
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attrVo){
		attrService.saveDetailed(attrVo);

        return R.ok();
    }

    /**
     * 08、修改属性
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo){


		attrService.updateDetailed(attrVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
