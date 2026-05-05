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
import java.time.LocalDateTime;

/**
 * Shopee Webhook 日志实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shopee_webhook_log")
@Schema(description = "Shopee Webhook 日志")
public class ShopeeWebhookLog extends Model<ShopeeWebhookLog> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("shop_id")
    @Schema(description = "关联的店铺 ID")
    private Long shopId;

    @TableField("webhook_type")
    @Schema(description = "Webhook 类型")
    private String webhookType;

    @TableField("webhook_id")
    @Schema(description = "Shopee Webhook ID")
    private Long webhookId;

    @TableField("resource_type")
    @Schema(description = "资源类型: order/product/customer")
    private String resourceType;

    @TableField("resource_id")
    @Schema(description = "资源 ID")
    private Long resourceId;

    @TableField("payload")
    @Schema(description = "Webhook 负载")
    private String payload;

    @TableField("headers")
    @Schema(description = "请求头")
    private String headers;

    @TableField("processed_status")
    @Schema(description = "处理状态: pending/success/failed")
    private String processedStatus;

    @TableField("error_message")
    @Schema(description = "错误信息")
    private String errorMessage;

    @TableField("received_time")
    @Schema(description = "接收时间")
    private LocalDateTime receivedTime;

    @TableField("processed_time")
    @Schema(description = "处理时间")
    private LocalDateTime processedTime;

    @TableField("retry_count")
    @Schema(description = "重试次数")
    private Integer retryCount;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
