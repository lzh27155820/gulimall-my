package com.liu.xyz.gulimall.ware.feignController;

import com.liu.xyz.common.to.SkuHasStockVo;
import com.liu.xyz.common.utils.R;
import com.liu.xyz.gulimall.ware.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create liu 2022-10-15
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuFeign {
    @Autowired
    private WareSkuService wareSkuService;
    @RequestMapping("hastStock")
   public R getHastStock(@RequestBody List<Long> skuIds){

        List<SkuHasStockVo> skuHasStockVos=wareSkuService.getHastStock(skuIds);
        return R.ok().put("data",skuHasStockVos);
    }
}
