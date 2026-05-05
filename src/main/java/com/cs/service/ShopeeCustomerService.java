package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.ShopeeCustomer;

/**
 * Shopee 客户服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface ShopeeCustomerService extends IService<ShopeeCustomer> {

    /**
     * 根据 Shopee 客户 ID 查询
     *
     * @param shopId     店铺 ID
     * @param customerId 客户 ID
     * @return 客户
     */
    ShopeeCustomer getByCustomerId(Long shopId, Long customerId);

    /**
     * 同步客户数据
     *
     * @param shopId  店铺 ID
     * @param daysAgo 同步最近多少天的数据
     * @return 同步的记录数
     */
    int syncCustomers(Long shopId, int daysAgo);

    /**
     * 标记为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);
}
