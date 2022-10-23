package com.liu.xyz.gulimall.product.feign;

import com.liu.xyz.common.to.BoundsTo;
import com.liu.xyz.common.to.SkuReductionTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create liu 2022-10-14
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {


    @RequestMapping("/coupon/spubounds/saveBounds")
    void saveBounds(@RequestBody BoundsTo boundsTo);
    @RequestMapping("/coupon/skuladder/ladder/reduction/price")
    void saveLadderAndReductionAndPrice(@RequestBody SkuReductionTo skuReductionTo);
}
