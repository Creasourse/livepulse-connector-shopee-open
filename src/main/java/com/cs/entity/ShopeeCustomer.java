package com.cs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Shopee 客户实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shopee_customer")
@Schema(description = "Shopee 客户")
public class ShopeeCustomer extends Model<ShopeeCustomer> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("shop_id")
    @Schema(description = "关联的店铺 ID")
    private Long shopId;

    @TableField("customer_id")
    @Schema(description = "Shopee 客户 ID")
    private Long customerId;

    @TableField("username")
    @Schema(description = "用户名")
    private String username;

    @TableField("email")
    @Schema(description = "客户邮箱")
    private String email;

    @TableField("phone")
    @Schema(description = "电话")
    private String phone;

    @TableField("first_name")
    @Schema(description = "名字")
    private String firstName;

    @TableField("last_name")
    @Schema(description = "姓氏")
    private String lastName;

    @TableField("orders_count")
    @Schema(description = "订单数量")
    private Integer ordersCount;

    @TableField("total_spent")
    @Schema(description = "总消费金额")
    private BigDecimal totalSpent;

    @TableField("average_order_value")
    @Schema(description = "平均订单价值")
    private BigDecimal averageOrderValue;

    @TableField("last_order_id")
    @Schema(description = "最后订单 ID")
    private Long lastOrderId;

    @TableField("last_order_sn")
    @Schema(description = "最后订单编号")
    private String lastOrderSn;

    @TableField("status")
    @Schema(description = "状态: active/inactive")
    private String status;

    @TableField("create_time_shopee")
    @Schema(description = "Shopee 创建时间")
    private LocalDateTime createTimeShopee;

    @TableField("update_time_shopee")
    @Schema(description = "Shopee 更新时间")
    private LocalDateTime updateTimeShopee;

    @TableField("processed")
    @Schema(description = "是否已处理")
    private Boolean processed;

    @TableField("processed_time")
    @Schema(description = "处理时间")
    private LocalDateTime processedTime;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
