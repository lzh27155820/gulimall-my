package com.liu.xyz.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.member.dao.MemberLevelDao;
import com.liu.xyz.gulimall.member.entity.MemberLevelEntity;
import com.liu.xyz.gulimall.member.service.MemberLevelService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevelEntity> implements MemberLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<MemberLevelEntity> wrapper = new QueryWrapper<>();

        String key =(String) params.get("key");

        if(!StringUtils.isEmpty(key)){
            wrapper.like("name",key);
        }
        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
             wrapper
        );

        return new PageUtils(page);
    }

}