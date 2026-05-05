package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.ShopeeProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * Shopee 产品 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface ShopeeProductMapper extends BaseMapper<ShopeeProduct> {
}
