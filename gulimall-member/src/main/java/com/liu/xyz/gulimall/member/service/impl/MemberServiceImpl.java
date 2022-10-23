package com.liu.xyz.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.member.dao.MemberDao;
import com.liu.xyz.gulimall.member.entity.MemberEntity;
import com.liu.xyz.gulimall.member.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key =(String) params.get("key");
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("id",key).or().like("name",key);
        }
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
             wrapper
        );

        return new PageUtils(page);
    }

}