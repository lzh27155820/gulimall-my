package com.liu.xyz.gulimall.product.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.myutils.ProductConstant;
import com.liu.xyz.common.to.BoundsTo;
import com.liu.xyz.common.to.SkuHasStockVo;
import com.liu.xyz.common.to.SkuReductionTo;
import com.liu.xyz.common.to.es.SkuESModel;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.common.utils.R;
import com.liu.xyz.gulimall.product.dao.SpuInfoDao;
import com.liu.xyz.gulimall.product.entity.*;
import com.liu.xyz.gulimall.product.feign.CouponFeignService;
import com.liu.xyz.gulimall.product.feign.SearchFeignSerive;
import com.liu.xyz.gulimall.product.feign.WareFeignService;
import com.liu.xyz.gulimall.product.service.*;
import com.liu.xyz.gulimall.product.vo.add.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private SkuImagesService skuImagesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {






        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuSaveVo(SpuSaveVo spuSaveVo) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        //1.保存基本的spu 保存每款商品
        BeanUtils.copyProperties(spuSaveVo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);
        Long spuId = spuInfoEntity.getId();
        //2.保存图片 详细信息 spu
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuId);
        descEntity.setDecript(String.join(",",decript));
        spuInfoDescService.save(descEntity);
        //3.保存图片 spu
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuId,images);
        //4.保存spu的参数规格 pms_spu_attr_value
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        productAttrValueService.saveBaseAttrs(baseAttrs,spuId);
        //保存spu积分 gulimall-sms-》sms_spu_bounds
        //5 保存当前spu对应的所有sku信息
        Bounds bounds = spuSaveVo.getBounds();
        BoundsTo boundsTo = new BoundsTo();
        BeanUtils.copyProperties(bounds,boundsTo);
        boundsTo.setSpuId(spuId);
        //5.1 服务与服务之间的传输To
        couponFeignService.saveBounds(boundsTo);
        //6.保存 sku信息
        List<Skus> skus = spuSaveVo.getSkus();
        if (skus!=null&&skus.size()>0){

            skus.forEach(sku->{
                String defaultImgUrl="";
                //6.1 获取默认图片
                for(Images e:sku.getImages()){
                    if(e.getDefaultImg()==1){
                        defaultImgUrl=e.getImgUrl();
                    }
                }
                //6.2保存每件商品信息
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku,skuInfoEntity);
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setSkuDefaultImg(defaultImgUrl);
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuId);
                skuInfoService.save(skuInfoEntity);
                //6.3 保存 销售属性
                List<Attr> attrList = sku.getAttr();
                skuSaleAttrValueService.saveAttr(skuInfoEntity.getSkuId(),attrList);
                //6.4
                List<Images> imagesList = sku.getImages();
                List<SkuImagesEntity> collect = imagesList.stream().map(obj -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuImagesEntity.setImgUrl(obj.getImgUrl());
                    skuImagesEntity.setDefaultImg(obj.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity->{
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(collect);
                //6.5
                //sku的优惠、满减等信息；gulimall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku,skuReductionTo);
                skuReductionTo.setSkuId(skuInfoEntity.getSkuId());
                if(skuReductionTo.getFullCount()>0||
                        skuReductionTo.getFullPrice().compareTo(new BigDecimal("0"))==1){

                    List<com.liu.xyz.common.to.MemberPrice> list = new
                            ArrayList<>();
                    List<MemberPrice> memberPrices = sku.getMemberPrice();
                    memberPrices.stream().forEach(obj->{
                        com.liu.xyz.common.to.MemberPrice memberPrice=new   com.liu.xyz.common.to.MemberPrice();
                        memberPrice.setPrice(obj.getPrice());
                        memberPrice.setName(obj.getName());
                        memberPrice.setId(obj.getId());
                        list.add(memberPrice);
                    });
                    skuReductionTo.setMemberPrice(list);
                    couponFeignService.saveLadderAndReductionAndPrice(skuReductionTo);
                }

            });
        }



    }

    @Autowired
    private AttrService attrService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private SearchFeignSerive searchFeignSerive;

    @Transactional
    @Override
    public void up(Long spuId) {

        //SkuESModel esModel = new SkuESModel();
        //1.查询 商品所保存的基础属性
        List<ProductAttrValueEntity> productAttrValueEntityList = productAttrValueService.list(
                new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        List<Long> AttrIds = productAttrValueEntityList.stream().map(obj -> {
            return obj.getAttrId();
        }).collect(Collectors.toList());
        //1.1 查询基础属性 是否支持索引
       List<Long> attrIdList=attrService.getSearchAttrIds(AttrIds);
       //1.2 去重
        HashSet<Long>  hashSet= new HashSet<>(attrIdList);
        //1.3 得出结果
        List<SkuESModel.Attrs> EsattrList = productAttrValueEntityList.stream().filter(obj -> {
            return hashSet.contains(obj.getAttrId());
        }).map(objf -> {
            SkuESModel.Attrs attrs = new SkuESModel.Attrs();
            attrs.setAttrId(objf.getAttrId());
            attrs.setAttrValue(objf.getAttrValue());
            attrs.setAttrName(objf.getAttrName());
            return attrs;
        }).collect(Collectors.toList());
        //2.根据spu 查询 sku 相当于 根据一款商品查询一件商品
        List<SkuInfoEntity> skuInfoEntityList = skuInfoService.list(
                new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));

        //2.3 远程得到库存
        List<Long> skuIds = skuInfoEntityList.stream().map(obj -> obj.getSkuId()).collect(Collectors.toList());
        R r =wareFeignService.getHastStock(skuIds);

        Map<Long, Boolean> map = new HashMap<>();

        List<SkuHasStockVo> stockVoList =(List<SkuHasStockVo>) r.get("data");
        Object data = r.get("data");
        List<SkuHasStockVo> list = JSON.parseObject(JSON.toJSONString(data), new TypeReference<List<SkuHasStockVo>>(){});
        map = list.stream().collect(Collectors.toMap(obj -> obj.getSkuId(), v -> v.getHastStock()));




//        for(Object e:stockVoList){
//            String s = JSON.toJSONString(e);
//            HashMap<Long, Boolean> hashMap =JSON.parseObject(s, new TypeReference<HashMap<Long, Boolean>>(){});
//
//
//            SkuHasStockVo vo = JSON.parseObject(s, SkuHasStockVo.class);
//
//            map.put(vo.getSkuId(),vo.getHastStock());
//        }
        Map<Long, Boolean> finalMap = map;
        if(skuInfoEntityList!=null&&skuInfoEntityList.size()>0){


            List<SkuESModel> collect = skuInfoEntityList.stream().map(obj -> {
                SkuESModel esModel = new SkuESModel();
                //2.1
                BeanUtils.copyProperties(obj, esModel);
                //2. 2 设置 skuPrice skuImg   attrsList
                esModel.setSkuPrice(obj.getPrice());
                esModel.setSkuImg(obj.getSkuDefaultImg());
                esModel.setAttrsList(EsattrList);
                //远程调用 hastStock hotScore
                if (finalMap == null) {
                    esModel.setHastStock(true);
                } else {
                    esModel.setHastStock(finalMap.get(obj.getSkuId()));
                }
                //热度评分
                esModel.setHotScore(0l);
                // brandName brandImg
                BrandEntity brandEntity = brandService.getById(obj.getBrandId());
                esModel.setBrandName(brandEntity.getName());
                esModel.setBrandImg(brandEntity.getLogo());

                //catalogName
                CategoryEntity categoryEntity = categoryService.getById(obj.getCatalogId());
                esModel.setCatalogName(categoryEntity.getName());
                return esModel;
            }).collect(Collectors.toList());


            //保存到es中
            R resp = searchFeignSerive.saveEs(collect);

            if(resp.getCode()==0){
                baseMapper.updateStatus(spuId, ProductConstant.StatusEnum.UP_SPU.getCode());
            }else {
                //TODO 重复调用问题
            }
        }



    }

}