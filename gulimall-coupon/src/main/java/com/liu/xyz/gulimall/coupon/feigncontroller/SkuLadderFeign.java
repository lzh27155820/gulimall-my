package com.liu.xyz.gulimall.coupon.feigncontroller;

import com.liu.xyz.common.to.SkuReductionTo;
import com.liu.xyz.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create liu 2022-10-14
 */
@RestController
@RequestMapping("coupon/skuladder")
public class SkuLadderFeign {

    @Autowired
    private SkuLadderService skuLadderService;

    /**
     *  远程调用 保存 优惠信息
     */
    @RequestMapping("/ladder/reduction/price")
    public void saveLadderAndReductionAndPrice(@RequestBody SkuReductionTo skuReductionTo){

        skuLadderService.saveLadderAndReductionAndPrice(skuReductionTo);
    }


}
