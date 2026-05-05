package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.ShopeeProduct;
import com.cs.mapper.ShopeeProductMapper;
import com.cs.service.ShopeeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Shopee 产品服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class ShopeeProductServiceImpl extends ServiceImpl<ShopeeProductMapper, ShopeeProduct> implements ShopeeProductService {

    private final ShopeeProductMapper productMapper;

    @Override
    public ShopeeProduct getByItemId(Long shopId, Long itemId) {
        LambdaQueryWrapper<ShopeeProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopeeProduct::getShopId, shopId)
                .eq(ShopeeProduct::getItemId, itemId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncProducts(Long shopId, int daysAgo) {
        // TODO: 实现 Shopee API 调用逻辑
        // 1. 调用 Shopee API 获取产品数据
        // 2. 批量插入或更新产品数据
        // 3. 更新同步日志
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        ShopeeProduct product = getById(id);
        if (product == null) {
            return false;
        }
        product.setProcessed(true);
        product.setProcessedTime(LocalDateTime.now());
        return updateById(product);
    }
}
