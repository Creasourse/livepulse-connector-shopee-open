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
 * Shopee 产品实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shopee_product")
@Schema(description = "Shopee 产品")
public class ShopeeProduct extends Model<ShopeeProduct> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("shop_id")
    @Schema(description = "关联的店铺 ID")
    private Long shopId;

    @TableField("item_id")
    @Schema(description = "Shopee 产品 ID")
    private Long itemId;

    @TableField("item_name")
    @Schema(description = "产品名称")
    private String itemName;

    @TableField("item_sku")
    @Schema(description = "产品 SKU")
    private String itemSku;

    @TableField("category_id")
    @Schema(description = "分类 ID")
    private Long categoryId;

    @TableField("status")
    @Schema(description = "状态: NORMAL/BANNED/DELETED/UNLIST")
    private String status;

    @TableField("description")
    @Schema(description = "产品描述")
    private String description;

    @TableField("item_short_desc")
    @Schema(description = "产品简短描述")
    private String itemShortDesc;

    @TableField("tags")
    @Schema(description = "产品标签")
    private String tags;

    @TableField("create_time_shopee")
    @Schema(description = "Shopee 创建时间")
    private LocalDateTime createTimeShopee;

    @TableField("update_time_shopee")
    @Schema(description = "Shopee 更新时间")
    private LocalDateTime updateTimeShopee;

    @TableField("stock")
    @Schema(description = "库存")
    private Integer stock;

    @TableField("sales")
    @Schema(description = "销量")
    private Integer sales;

    @TableField("price")
    @Schema(description = "价格")
    private BigDecimal price;

    @TableField("original_price")
    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @TableField("has_variation")
    @Schema(description = "是否有变体")
    private Boolean hasVariation;

    @TableField("variation_count")
    @Schema(description = "变体数量")
    private Integer variationCount;

    @TableField("image_count")
    @Schema(description = "图片数量")
    private Integer imageCount;

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
