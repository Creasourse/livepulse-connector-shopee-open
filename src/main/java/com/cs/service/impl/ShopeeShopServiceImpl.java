package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.ShopeeShop;
import com.cs.mapper.ShopeeShopMapper;
import com.cs.service.ShopeeOrderService;
import com.cs.service.ShopeeProductService;
import com.cs.service.ShopeeCustomerService;
import com.cs.service.ShopeeShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Shopee 店铺配置服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class ShopeeShopServiceImpl extends ServiceImpl<ShopeeShopMapper, ShopeeShop> implements ShopeeShopService {

    private final ShopeeShopMapper shopMapper;

    @Lazy
    @Autowired
    private ShopeeOrderService orderService;

    @Lazy
    @Autowired
    private ShopeeProductService productService;

    @Lazy
    @Autowired
    private ShopeeCustomerService customerService;

    @Override
    public ShopeeShop getByShopId(Long shopId) {
        LambdaQueryWrapper<ShopeeShop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopeeShop::getShopId, shopId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableShop(Long id) {
        ShopeeShop shop = getById(id);
        if (shop == null) {
            return false;
        }
        shop.setEnabled(true);
        return updateById(shop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableShop(Long id) {
        ShopeeShop shop = getById(id);
        if (shop == null) {
            return false;
        }
        shop.setEnabled(false);
        return updateById(shop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncStatus(Long id, String syncStatus) {
        ShopeeShop shop = getById(id);
        if (shop == null) {
            return false;
        }
        shop.setSyncStatus(syncStatus);
        return updateById(shop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLastSyncTime(Long id, String syncType) {
        ShopeeShop shop = getById(id);
        if (shop == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        switch (syncType.toLowerCase()) {
            case "order":
                shop.setLastOrderSyncTime(now);
                break;
            case "product":
                shop.setLastProductSyncTime(now);
                break;
            case "customer":
                shop.setLastCustomerSyncTime(now);
                break;
            default:
                return false;
        }

        return updateById(shop);
    }
}
