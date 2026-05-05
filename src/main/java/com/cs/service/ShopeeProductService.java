package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.ShopeeProduct;

/**
 * Shopee 产品服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface ShopeeProductService extends IService<ShopeeProduct> {

    /**
     * 根据 Shopee 产品 ID 查询
     *
     * @param shopId 店铺 ID
     * @param itemId 产品 ID
     * @return 产品
     */
    ShopeeProduct getByItemId(Long shopId, Long itemId);

    /**
     * 同步产品数据
     *
     * @param shopId  店铺 ID
     * @param daysAgo 同步最近多少天的数据
     * @return 同步的记录数
     */
    int syncProducts(Long shopId, int daysAgo);

    /**
     * 标记为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);
}
