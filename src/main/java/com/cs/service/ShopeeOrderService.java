package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.ShopeeOrder;

/**
 * Shopee 订单服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface ShopeeOrderService extends IService<ShopeeOrder> {

    /**
     * 根据 Shopee 订单编号查询
     *
     * @param shopId  店铺 ID
     * @param orderSn 订单编号
     * @return 订单
     */
    ShopeeOrder getByOrderSn(Long shopId, String orderSn);

    /**
     * 同步订单数据
     *
     * @param shopId   店铺 ID
     * @param daysAgo  同步最近多少天的数据
     * @return 同步的记录数
     */
    int syncOrders(Long shopId, int daysAgo);

    /**
     * 标记为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);
}
