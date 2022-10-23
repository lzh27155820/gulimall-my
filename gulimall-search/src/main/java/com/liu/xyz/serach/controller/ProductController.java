package com.liu.xyz.serach.controller;

import com.liu.xyz.common.to.es.SkuESModel;
import com.liu.xyz.common.utils.R;
import com.liu.xyz.serach.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create liu 2022-10-15
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @RequestMapping("/search/up/es")
    public R saveEs(@RequestBody List<SkuESModel> esModelList){

        boolean b=productService.upDateEs(esModelList);

        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }
}
