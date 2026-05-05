package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.ShopeeWebhookLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Shopee Webhook 日志 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface ShopeeWebhookLogMapper extends BaseMapper<ShopeeWebhookLog> {
}
