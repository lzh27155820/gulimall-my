package com.liu.xyz.gulimall.coupon.feigncontroller;

import com.liu.xyz.common.to.BoundsTo;
import com.liu.xyz.gulimall.coupon.service.SpuBoundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create liu 2022-10-14
 */
@RestController
@RequestMapping("coupon/spubounds")
public class SpuBoundsFeign {

    @Autowired
    private SpuBoundsService spuBoundsService;

    @RequestMapping("/saveBounds")
    public void saveBounds(@RequestBody BoundsTo boundsTo){
        spuBoundsService.saveBounds(boundsTo);
    }
}
