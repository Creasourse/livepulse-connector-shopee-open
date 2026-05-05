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
 * Shopee 订单实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shopee_order")
@Schema(description = "Shopee 订单")
public class ShopeeOrder extends Model<ShopeeOrder> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("shop_id")
    @Schema(description = "关联的店铺 ID")
    private Long shopId;

    @TableField("order_sn")
    @Schema(description = "Shopee 订单编号")
    private String orderSn;

    @TableField("order_number")
    @Schema(description = "订单号")
    private String orderNumber;

    @TableField("email")
    @Schema(description = "客户邮箱")
    private String email;

    @TableField("phone")
    @Schema(description = "客户电话")
    private String phone;

    @TableField("recipient_name")
    @Schema(description = "收件人姓名")
    private String recipientName;

    @TableField("receiver_address")
    @Schema(description = "收件地址")
    private String receiverAddress;

    @TableField("payment_method")
    @Schema(description = "支付方式")
    private String paymentMethod;

    @TableField("order_status")
    @Schema(description = "订单状态: UNPAID/READY_TO_SHIP/SHIPPED/COMPLETED/CANCELLED")
    private String orderStatus;

    @TableField("currency")
    @Schema(description = "货币代码")
    private String currency;

    @TableField("total_amount")
    @Schema(description = "订单总金额")
    private BigDecimal totalAmount;

    @TableField("subtotal")
    @Schema(description = "小计")
    private BigDecimal subtotal;

    @TableField("total_shipping_fee")
    @Schema(description = "总运费")
    private BigDecimal totalShippingFee;

    @TableField("buyer_paid_amount")
    @Schema(description = "买家支付金额")
    private BigDecimal buyerPaidAmount;

    @TableField("seller_income")
    @Schema(description = "卖家收入")
    private BigDecimal sellerIncome;

    @TableField("create_time_shopee")
    @Schema(description = "Shopee 创建时间")
    private LocalDateTime createTimeShopee;

    @TableField("update_time_shopee")
    @Schema(description = "Shopee 更新时间")
    private LocalDateTime updateTimeShopee;

    @TableField("pay_time")
    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @TableField("ship_time")
    @Schema(description = "发货时间")
    private LocalDateTime shipTime;

    @TableField("receive_time")
    @Schema(description = "收货时间")
    private LocalDateTime receiveTime;

    @TableField("cancel_reason")
    @Schema(description = "取消原因")
    private String cancelReason;

    @TableField("customer_id")
    @Schema(description = "客户 ID")
    private Long customerId;

    @TableField("customer_email")
    @Schema(description = "客户邮箱")
    private String customerEmail;

    @TableField("item_count")
    @Schema(description = "订单项数量")
    private Integer itemCount;

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
