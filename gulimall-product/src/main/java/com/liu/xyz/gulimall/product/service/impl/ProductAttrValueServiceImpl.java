package com.liu.xyz.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.xyz.common.utils.PageUtils;
import com.liu.xyz.common.utils.Query;
import com.liu.xyz.gulimall.product.dao.ProductAttrValueDao;
import com.liu.xyz.gulimall.product.entity.AttrEntity;
import com.liu.xyz.gulimall.product.entity.ProductAttrValueEntity;
import com.liu.xyz.gulimall.product.service.AttrService;
import com.liu.xyz.gulimall.product.service.ProductAttrValueService;
import com.liu.xyz.gulimall.product.vo.ProductVo;
import com.liu.xyz.gulimall.product.vo.add.BaseAttrs;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private AttrService attrService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBaseAttrs(List<BaseAttrs> baseAttrs, Long spuId) {

        if(baseAttrs!=null&&baseAttrs.size()>0){
            List<ProductAttrValueEntity> collect = baseAttrs.stream().map(obj -> {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                productAttrValueEntity.setSpuId(spuId);
                productAttrValueEntity.setAttrValue(obj.getAttrValues());
                productAttrValueEntity.setQuickShow(obj.getShowDesc());
                productAttrValueEntity.setAttrId(obj.getAttrId());
                //
                AttrEntity attrEntity = attrService.getById(obj.getAttrId());
                if (attrEntity != null) {
                    productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                }
                return productAttrValueEntity;
            }).collect(Collectors.toList());

            this.saveBatch(collect);
        }

    }

    @Override
    public List<ProductAttrValueEntity> BaseListforspuSpuId(Long spuId) {

        List<ProductAttrValueEntity> list = this.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        return list;
    }

    @Override
    public void updateToSpuId(Long spuId, List<ProductVo> list) {



//        List<ProductAttrValueEntity> collect = list.stream().map(obj -> {
//            ProductAttrValueEntity productAttrValue = new ProductAttrValueEntity();
//            BeanUtils.copyProperties(obj, productAttrValue);
//            return productAttrValue;
//        }).collect(Collectors.toList());
        list.stream().forEach(obj->{

            UpdateWrapper<ProductAttrValueEntity> wrapper =
                    new UpdateWrapper<ProductAttrValueEntity>().eq("spu_id", spuId);
            wrapper.eq("attr_id",obj.getAttrId());
            wrapper.eq("attr_name",obj.getAttrName());
                        ProductAttrValueEntity productAttrValue = new ProductAttrValueEntity();
            BeanUtils.copyProperties(obj, productAttrValue);
            this.update(productAttrValue,wrapper);
        });

        // baseMapper.updateToSpuId(spuId,list);
    }

}