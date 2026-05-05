package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.ShopeeProduct;
import com.cs.service.ShopeeProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Shopee 产品控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/shopee/product")
@RequiredArgsConstructor
@Tag(name = "Shopee 产品管理", description = "Shopee 产品数据查询接口")
public class ShopeeProductController {

    private final ShopeeProductService productService;

    @GetMapping("/{id}")
    @Operation(summary = "获取产品详情", description = "根据 ID 获取产品详情")
    public ResponseEntity<ShopeeProduct> getProduct(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        ShopeeProduct product = productService.getById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @Operation(summary = "分页查询产品", description = "分页查询 Shopee 产品列表")
    public ResponseEntity<Page<ShopeeProduct>> listProducts(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "店铺 ID") @RequestParam(required = false) Long shopId,
            @Parameter(description = "产品状态") @RequestParam(required = false) String status,
            @Parameter(description = "产品名称") @RequestParam(required = false) String productName) {
        Page<ShopeeProduct> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ShopeeProduct> wrapper = new LambdaQueryWrapper<>();

        if (shopId != null) {
            wrapper.eq(ShopeeProduct::getShopId, shopId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq(ShopeeProduct::getStatus, status);
        }
        if (productName != null && !productName.isEmpty()) {
            wrapper.like(ShopeeProduct::getItemName, productName);
        }

        wrapper.orderByDesc(ShopeeProduct::getCreateTimeShopee);
        Page<ShopeeProduct> result = productService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将产品标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        productService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
