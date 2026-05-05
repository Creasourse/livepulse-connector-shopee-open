package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.ShopeeShop;

/**
 * Shopee 店铺配置服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface ShopeeShopService extends IService<ShopeeShop> {

    /**
     * 根据 Shopee 店铺 ID 查询
     *
     * @param shopId Shopee 店铺 ID
     * @return 店铺配置
     */
    ShopeeShop getByShopId(Long shopId);

    /**
     * 启用店铺
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean enableShop(Long id);

    /**
     * 禁用店铺
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean disableShop(Long id);

    /**
     * 更新同步状态
     *
     * @param id        主键 ID
     * @param syncStatus 同步状态
     * @return 是否成功
     */
    boolean updateSyncStatus(Long id, String syncStatus);

    /**
     * 更新最后同步时间
     *
     * @param id    主键 ID
     * @param syncType 同步类型
     * @return 是否成功
     */
    boolean updateLastSyncTime(Long id, String syncType);
}
