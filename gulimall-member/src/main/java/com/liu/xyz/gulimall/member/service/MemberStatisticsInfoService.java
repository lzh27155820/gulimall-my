package com.liu.xyz.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.gulimall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-09-30 14:33:05
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

