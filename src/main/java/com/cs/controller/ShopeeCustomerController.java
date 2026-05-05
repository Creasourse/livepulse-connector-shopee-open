package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.ShopeeCustomer;
import com.cs.service.ShopeeCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Shopee 客户控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/shopee/customer")
@RequiredArgsConstructor
@Tag(name = "Shopee 客户管理", description = "Shopee 客户数据查询接口")
public class ShopeeCustomerController {

    private final ShopeeCustomerService customerService;

    @GetMapping("/{id}")
    @Operation(summary = "获取客户详情", description = "根据 ID 获取客户详情")
    public ResponseEntity<ShopeeCustomer> getCustomer(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        ShopeeCustomer customer = customerService.getById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    @Operation(summary = "分页查询客户", description = "分页查询 Shopee 客户列表")
    public ResponseEntity<Page<ShopeeCustomer>> listCustomers(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "店铺 ID") @RequestParam(required = false) Long shopId,
            @Parameter(description = "客户邮箱") @RequestParam(required = false) String email,
            @Parameter(description = "客户状态") @RequestParam(required = false) String status) {
        Page<ShopeeCustomer> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ShopeeCustomer> wrapper = new LambdaQueryWrapper<>();

        if (shopId != null) {
            wrapper.eq(ShopeeCustomer::getShopId, shopId);
        }
        if (email != null && !email.isEmpty()) {
            wrapper.like(ShopeeCustomer::getEmail, email);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq(ShopeeCustomer::getStatus, status);
        }

        wrapper.orderByDesc(ShopeeCustomer::getCreateTimeShopee);
        Page<ShopeeCustomer> result = customerService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将客户标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        customerService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
