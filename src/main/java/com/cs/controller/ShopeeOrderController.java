package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.ShopeeOrder;
import com.cs.service.ShopeeOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Shopee 订单控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/shopee/order")
@RequiredArgsConstructor
@Tag(name = "Shopee 订单管理", description = "Shopee 订单数据查询接口")
public class ShopeeOrderController {

    private final ShopeeOrderService orderService;

    @GetMapping("/{id}")
    @Operation(summary = "获取订单详情", description = "根据 ID 获取订单详情")
    public ResponseEntity<ShopeeOrder> getOrder(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        ShopeeOrder order = orderService.getById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @Operation(summary = "分页查询订单", description = "分页查询 Shopee 订单列表")
    public ResponseEntity<Page<ShopeeOrder>> listOrders(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "店铺 ID") @RequestParam(required = false) Long shopId,
            @Parameter(description = "订单状态") @RequestParam(required = false) String orderStatus,
            @Parameter(description = "客户邮箱") @RequestParam(required = false) String customerEmail,
            @Parameter(description = "开始日期") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Page<ShopeeOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ShopeeOrder> wrapper = new LambdaQueryWrapper<>();

        if (shopId != null) {
            wrapper.eq(ShopeeOrder::getShopId, shopId);
        }
        if (orderStatus != null && !orderStatus.isEmpty()) {
            wrapper.eq(ShopeeOrder::getOrderStatus, orderStatus);
        }
        if (customerEmail != null && !customerEmail.isEmpty()) {
            wrapper.like(ShopeeOrder::getCustomerEmail, customerEmail);
        }
        if (startDate != null) {
            wrapper.ge(ShopeeOrder::getCreateTimeShopee, startDate.atStartOfDay());
        }
        if (endDate != null) {
            wrapper.le(ShopeeOrder::getCreateTimeShopee, endDate.atTime(23, 59, 59));
        }

        wrapper.orderByDesc(ShopeeOrder::getCreateTimeShopee);
        Page<ShopeeOrder> result = orderService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将订单标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        orderService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
