package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.ShopeeShop;
import com.cs.service.ShopeeShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Shopee 店铺配置控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/shopee/shop")
@RequiredArgsConstructor
@Tag(name = "Shopee 店铺管理", description = "Shopee 店铺配置管理接口")
public class ShopeeShopController {

    private final ShopeeShopService shopService;

    @PostMapping
    @Operation(summary = "添加店铺配置", description = "添加新的 Shopee 店铺配置")
    public ResponseEntity<ShopeeShop> addShop(@RequestBody ShopeeShop shop) {
        shop.setCreateTime(java.time.LocalDateTime.now());
        shop.setUpdateTime(java.time.LocalDateTime.now());
        shop.setEnabled(true);
        shop.setSyncStatus("pending");
        shopService.save(shop);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新店铺配置", description = "更新 Shopee 店铺配置")
    public ResponseEntity<ShopeeShop> updateShop(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @RequestBody ShopeeShop shop) {
        shop.setId(id);
        shop.setUpdateTime(java.time.LocalDateTime.now());
        shopService.updateById(shop);
        return ResponseEntity.ok(shop);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除店铺配置", description = "删除 Shopee 店铺配置")
    public ResponseEntity<Void> deleteShop(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        shopService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取店铺配置", description = "根据 ID 获取店铺配置")
    public ResponseEntity<ShopeeShop> getShop(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        ShopeeShop shop = shopService.getById(id);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shop);
    }

    @GetMapping
    @Operation(summary = "分页查询店铺配置", description = "分页查询 Shopee 店铺配置列表")
    public ResponseEntity<Page<ShopeeShop>> listShops(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "店铺名称") @RequestParam(required = false) String shopName) {
        Page<ShopeeShop> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ShopeeShop> wrapper = new LambdaQueryWrapper<>();
        if (shopName != null && !shopName.isEmpty()) {
            wrapper.like(ShopeeShop::getShopName, shopName);
        }
        wrapper.orderByDesc(ShopeeShop::getCreateTime);
        Page<ShopeeShop> result = shopService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/enable")
    @Operation(summary = "启用店铺", description = "启用指定的店铺")
    public ResponseEntity<Void> enableShop(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        shopService.enableShop(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disable")
    @Operation(summary = "禁用店铺", description = "禁用指定的店铺")
    public ResponseEntity<Void> disableShop(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        shopService.disableShop(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sync/orders")
    @Operation(summary = "手动同步订单", description = "手动触发订单数据同步")
    public ResponseEntity<String> syncOrders(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现订单同步逻辑
        return ResponseEntity.ok("订单同步任务已提交");
    }

    @PostMapping("/{id}/sync/products")
    @Operation(summary = "手动同步产品", description = "手动触发产品数据同步")
    public ResponseEntity<String> syncProducts(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现产品同步逻辑
        return ResponseEntity.ok("产品同步任务已提交");
    }

    @PostMapping("/{id}/sync/customers")
    @Operation(summary = "手动同步客户", description = "手动触发客户数据同步")
    public ResponseEntity<String> syncCustomers(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现客户同步逻辑
        return ResponseEntity.ok("客户同步任务已提交");
    }
}
