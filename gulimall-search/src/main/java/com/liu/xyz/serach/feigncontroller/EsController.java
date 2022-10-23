package com.liu.xyz.serach.feigncontroller;

import com.liu.xyz.serach.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create liu 2022-10-23
 */
@RestController
@RequestMapping("/search/up")
public class EsController {

    @Autowired
    private ProductService productService;
}
