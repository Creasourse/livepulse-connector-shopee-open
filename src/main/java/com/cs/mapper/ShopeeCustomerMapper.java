package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.ShopeeCustomer;
import org.apache.ibatis.annotations.Mapper;

/**
 * Shopee 客户 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface ShopeeCustomerMapper extends BaseMapper<ShopeeCustomer> {
}
