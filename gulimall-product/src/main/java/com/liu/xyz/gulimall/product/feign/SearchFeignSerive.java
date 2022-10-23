package com.liu.xyz.gulimall.product.feign;

import com.liu.xyz.common.to.es.SkuESModel;
import com.liu.xyz.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * create liu 2022-10-15
 */
@FeignClient("gulimall-search")
public interface SearchFeignSerive {

//     @RequestMapping("/search/up/es")
//     R saveEs(@RequestBody List<SkuESModel> esModelList);
     @RequestMapping("/search/up/es")
     public R saveEs(@RequestBody List<SkuESModel> esModelList);
}
