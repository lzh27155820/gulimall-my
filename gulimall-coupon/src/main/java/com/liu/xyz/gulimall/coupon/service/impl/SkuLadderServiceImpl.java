package com.liu.xyz.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.to.MemberPrice;
import com.liu.xyz.common.to.SkuReductionTo;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.coupon.dao.SkuLadderDao;
import com.liu.xyz.gulimall.coupon.entity.MemberPriceEntity;
import com.liu.xyz.gulimall.coupon.entity.SkuFullReductionEntity;
import com.liu.xyz.gulimall.coupon.entity.SkuLadderEntity;
import com.liu.xyz.gulimall.coupon.service.MemberPriceService;
import com.liu.xyz.gulimall.coupon.service.SkuFullReductionService;
import com.liu.xyz.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuLadderService")
public class SkuLadderServiceImpl extends ServiceImpl<SkuLadderDao, SkuLadderEntity> implements SkuLadderService {

    @Autowired
    private SkuFullReductionService skuFullReductionService;
    @Autowired
    private MemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuLadderEntity> page = this.page(
                new Query<SkuLadderEntity>().getPage(params),
                new QueryWrapper<SkuLadderEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveLadderAndReductionAndPrice(SkuReductionTo skuReductionTo) {
        //1.满多少元 优惠
        SkuFullReductionEntity reduction = new SkuFullReductionEntity();
        reduction.setSkuId(skuReductionTo.getSkuId());
        reduction.setAddOther(skuReductionTo.getPriceStatus());
        reduction.setFullPrice(skuReductionTo.getFullPrice());
        reduction.setReducePrice(skuReductionTo.getReducePrice());
        //1.1优惠0元不添加
        if(reduction.getFullPrice().compareTo(new BigDecimal("0"))==1){
            skuFullReductionService.save(reduction);
        }


        //2.满多少件 优惠
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());

        this.save(skuLadderEntity);
        //3.保存会员价格
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();

        List<MemberPriceEntity> collect = memberPrice.stream().map(obj -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberPrice(obj.getPrice());
            memberPriceEntity.setMemberLevelName(obj.getName());
            memberPriceEntity.setAddOther(1);
            memberPriceEntity.setMemberLevelId(obj.getId());
            return memberPriceEntity;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collect);
    }

}