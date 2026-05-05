package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.ShopeeOrder;
import com.cs.entity.ShopeeShop;
import com.cs.mapper.ShopeeOrderMapper;
import com.cs.service.ShopeeOrderService;
import com.cs.service.ShopeeShopService;
import com.cs.service.ShopeeSyncLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Shopee 订单服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class ShopeeOrderServiceImpl extends ServiceImpl<ShopeeOrderMapper, ShopeeOrder> implements ShopeeOrderService {

    private final ShopeeOrderMapper orderMapper;

    @Lazy
    @Autowired
    private ShopeeShopService shopService;

    @Lazy
    @Autowired
    private ShopeeSyncLogService syncLogService;

    @Override
    public ShopeeOrder getByOrderSn(Long shopId, String orderSn) {
        LambdaQueryWrapper<ShopeeOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopeeOrder::getShopId, shopId)
                .eq(ShopeeOrder::getOrderSn, orderSn);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncOrders(Long shopId, int daysAgo) {
        // TODO: 实现 Shopee API 调用逻辑
        // 1. 调用 Shopee API 获取订单数据
        // 2. 批量插入或更新订单数据
        // 3. 更新同步日志
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        ShopeeOrder order = getById(id);
        if (order == null) {
            return false;
        }
        order.setProcessed(true);
        order.setProcessedTime(LocalDateTime.now());
        return updateById(order);
    }
}
