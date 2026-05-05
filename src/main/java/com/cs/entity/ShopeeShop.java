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
 * Shopee 店铺配置实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shopee_shop")
@Schema(description = "Shopee 店铺配置")
public class ShopeeShop extends Model<ShopeeShop> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("shop_id")
    @Schema(description = "Shopee 店铺 ID")
    private Long shopId;

    @TableField("shop_name")
    @Schema(description = "店铺名称")
    private String shopName;

    @TableField("shop_url")
    @Schema(description = "店铺 URL")
    private String shopUrl;

    @TableField("region")
    @Schema(description = "地区: TW/MY/TH/ID/VN/PH/SG")
    private String region;

    @TableField("access_token")
    @Schema(description = "访问令牌")
    private String accessToken;

    @TableField("partner_id")
    @Schema(description = "合作伙伴 ID")
    private Long partnerId;

    @TableField("partner_key")
    @Schema(description = "合作伙伴密钥")
    private String partnerKey;

    @TableField("enabled")
    @Schema(description = "是否启用")
    private Boolean enabled;

    @TableField("sync_status")
    @Schema(description = "同步状态: pending/syncing/success/failed")
    private String syncStatus;

    @TableField("last_order_sync_time")
    @Schema(description = "最后订单同步时间")
    private LocalDateTime lastOrderSyncTime;

    @TableField("last_product_sync_time")
    @Schema(description = "最后产品同步时间")
    private LocalDateTime lastProductSyncTime;

    @TableField("last_customer_sync_time")
    @Schema(description = "最后客户同步时间")
    private LocalDateTime lastCustomerSyncTime;

    @TableField("webhook_enabled")
    @Schema(description = "是否启用 Webhook")
    private Boolean webhookEnabled;

    @TableField("webhook_url")
    @Schema(description = "Webhook 回调 URL")
    private String webhookUrl;

    @TableField("last_error_message")
    @Schema(description = "最后错误信息")
    private String lastErrorMessage;

    @TableField("retry_count")
    @Schema(description = "重试次数")
    private Integer retryCount;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableField("create_by")
    @Schema(description = "创建人")
    private String createBy;

    @TableField("update_by")
    @Schema(description = "更新人")
    private String updateBy;
}
