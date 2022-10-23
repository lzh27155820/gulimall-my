package com.liu.xyz.thirdparty.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * create liu 2022-10-03
 */
public interface OSSService {

    String uploadBrandLogo(MultipartFile multipartFile);
}
