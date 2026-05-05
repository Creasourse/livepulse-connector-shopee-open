package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.ShopeeOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * Shopee 订单 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface ShopeeOrderMapper extends BaseMapper<ShopeeOrder> {
}
