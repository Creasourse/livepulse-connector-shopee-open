package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.ShopeeCustomer;
import com.cs.mapper.ShopeeCustomerMapper;
import com.cs.service.ShopeeCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Shopee 客户服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class ShopeeCustomerServiceImpl extends ServiceImpl<ShopeeCustomerMapper, ShopeeCustomer> implements ShopeeCustomerService {

    private final ShopeeCustomerMapper customerMapper;

    @Override
    public ShopeeCustomer getByCustomerId(Long shopId, Long customerId) {
        LambdaQueryWrapper<ShopeeCustomer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopeeCustomer::getShopId, shopId)
                .eq(ShopeeCustomer::getCustomerId, customerId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncCustomers(Long shopId, int daysAgo) {
        // TODO: 实现 Shopee API 调用逻辑
        // 1. 调用 Shopee API 获取客户数据
        // 2. 批量插入或更新客户数据
        // 3. 更新同步日志
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        ShopeeCustomer customer = getById(id);
        if (customer == null) {
            return false;
        }
        customer.setProcessed(true);
        customer.setProcessedTime(LocalDateTime.now());
        return updateById(customer);
    }
}
