-- Shopee 连接器 PostgreSQL 表结构
-- 版本: 2.0
-- 说明: 支持多店铺管理、订单/产品/客户同步、Webhook 事件订阅

-- ============================================
-- 清理已存在的表（按依赖关系顺序）
-- ============================================
DROP TABLE IF EXISTS shopee_order CASCADE;
DROP TABLE IF EXISTS shopee_product CASCADE;
DROP TABLE IF EXISTS shopee_customer CASCADE;
DROP TABLE IF EXISTS shopee_webhook_log CASCADE;
DROP TABLE IF EXISTS shopee_sync_log CASCADE;
DROP TABLE IF EXISTS shopee_shop CASCADE;

-- ============================================
-- 1. Shopee 店铺配置表
-- ============================================
CREATE TABLE shopee_shop (
    id BIGSERIAL PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    shop_name VARCHAR(500) NOT NULL,
    shop_url VARCHAR(500),
    region VARCHAR(50),
    access_token VARCHAR(500) NOT NULL,
    partner_id BIGINT NOT NULL,
    partner_key VARCHAR(500) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    sync_status VARCHAR(50) DEFAULT 'pending',
    last_order_sync_time TIMESTAMP,
    last_product_sync_time TIMESTAMP,
    last_customer_sync_time TIMESTAMP,
    webhook_enabled BOOLEAN DEFAULT FALSE,
    webhook_url VARCHAR(500),
    last_error_message TEXT,
    retry_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(100),
    update_by VARCHAR(100),
    CONSTRAINT uk_shopee_shop_id UNIQUE (shop_id)
);

-- 表注释
COMMENT ON TABLE shopee_shop IS 'Shopee 店铺配置表';
COMMENT ON COLUMN shopee_shop.shop_id IS 'Shopee 店铺 ID';
COMMENT ON COLUMN shopee_shop.shop_name IS '店铺名称';
COMMENT ON COLUMN shopee_shop.shop_url IS '店铺 URL';
COMMENT ON COLUMN shopee_shop.region IS '地区: TW/MY/TH/ID/VN/PH/SG';
COMMENT ON COLUMN shopee_shop.access_token IS '访问令牌';
COMMENT ON COLUMN shopee_shop.partner_id IS '合作伙伴 ID';
COMMENT ON COLUMN shopee_shop.partner_key IS '合作伙伴密钥';
COMMENT ON COLUMN shopee_shop.enabled IS '是否启用';
COMMENT ON COLUMN shopee_shop.sync_status IS '同步状态: pending/syncing/success/failed';
COMMENT ON COLUMN shopee_shop.last_order_sync_time IS '最后订单同步时间';
COMMENT ON COLUMN shopee_shop.last_product_sync_time IS '最后产品同步时间';
COMMENT ON COLUMN shopee_shop.last_customer_sync_time IS '最后客户同步时间';
COMMENT ON COLUMN shopee_shop.webhook_enabled IS '是否启用 Webhook';
COMMENT ON COLUMN shopee_shop.webhook_url IS 'Webhook 回调 URL';
COMMENT ON COLUMN shopee_shop.last_error_message IS '最后错误信息';
COMMENT ON COLUMN shopee_shop.retry_count IS '重试次数';
COMMENT ON COLUMN shopee_shop.create_time IS '创建时间';
COMMENT ON COLUMN shopee_shop.update_time IS '更新时间';
COMMENT ON COLUMN shopee_shop.create_by IS '创建人';
COMMENT ON COLUMN shopee_shop.update_by IS '更新人';

-- ============================================
-- 2. Shopee 订单表
-- ============================================
CREATE TABLE shopee_order (
    id BIGSERIAL PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    order_sn VARCHAR(255) NOT NULL,
    order_number VARCHAR(255),
    email VARCHAR(500),
    phone VARCHAR(100),
    recipient_name VARCHAR(500),
    receiver_address VARCHAR(1000),
    payment_method VARCHAR(100),
    order_status VARCHAR(50),
    currency VARCHAR(10),
    total_amount DECIMAL(15, 2),
    subtotal DECIMAL(15, 2),
    total_shipping_fee DECIMAL(15, 2),
    buyer_paid_amount DECIMAL(15, 2),
    seller_income DECIMAL(15, 2),
    create_time_shopee TIMESTAMP,
    update_time_shopee TIMESTAMP,
    pay_time TIMESTAMP,
    ship_time TIMESTAMP,
    receive_time TIMESTAMP,
    cancel_reason VARCHAR(500),
    customer_id BIGINT,
    customer_email VARCHAR(500),
    item_count INT DEFAULT 0,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_shop FOREIGN KEY (shop_id) REFERENCES shopee_shop(id) ON DELETE CASCADE,
    CONSTRAINT uk_shopee_order_sn UNIQUE (shop_id, order_sn)
);

CREATE INDEX idx_shopee_order_shop_id ON shopee_order(shop_id);
CREATE INDEX idx_shopee_order_create_time ON shopee_order(create_time_shopee);
CREATE INDEX idx_shopee_order_status ON shopee_order(order_status);
CREATE INDEX idx_shopee_order_customer_email ON shopee_order(customer_email);

-- 表注释
COMMENT ON TABLE shopee_order IS 'Shopee 订单表';
COMMENT ON COLUMN shopee_order.shop_id IS '关联的店铺 ID';
COMMENT ON COLUMN shopee_order.order_sn IS 'Shopee 订单编号';
COMMENT ON COLUMN shopee_order.order_number IS '订单号';
COMMENT ON COLUMN shopee_order.email IS '客户邮箱';
COMMENT ON COLUMN shopee_order.phone IS '客户电话';
COMMENT ON COLUMN shopee_order.recipient_name IS '收件人姓名';
COMMENT ON COLUMN shopee_order.receiver_address IS '收件地址';
COMMENT ON COLUMN shopee_order.payment_method IS '支付方式';
COMMENT ON COLUMN shopee_order.order_status IS '订单状态: UNPAID/READY_TO_SHIP/SHIPPED/COMPLETED/CANCELLED/INVOICE_PENDING';
COMMENT ON COLUMN shopee_order.currency IS '货币代码';
COMMENT ON COLUMN shopee_order.total_amount IS '订单总金额';
COMMENT ON COLUMN shopee_order.subtotal IS '小计';
COMMENT ON COLUMN shopee_order.total_shipping_fee IS '总运费';
COMMENT ON COLUMN shopee_order.buyer_paid_amount IS '买家支付金额';
COMMENT ON COLUMN shopee_order.seller_income IS '卖家收入';
COMMENT ON COLUMN shopee_order.create_time_shopee IS 'Shopee 创建时间';
COMMENT ON COLUMN shopee_order.update_time_shopee IS 'Shopee 更新时间';
COMMENT ON COLUMN shopee_order.pay_time IS '支付时间';
COMMENT ON COLUMN shopee_order.ship_time IS '发货时间';
COMMENT ON COLUMN shopee_order.receive_time IS '收货时间';
COMMENT ON COLUMN shopee_order.cancel_reason IS '取消原因';
COMMENT ON COLUMN shopee_order.customer_id IS '客户 ID';
COMMENT ON COLUMN shopee_order.customer_email IS '客户邮箱';
COMMENT ON COLUMN shopee_order.item_count IS '订单项数量';
COMMENT ON COLUMN shopee_order.processed IS '是否已处理';
COMMENT ON COLUMN shopee_order.processed_time IS '处理时间';
COMMENT ON COLUMN shopee_order.create_time IS '创建时间';
COMMENT ON COLUMN shopee_order.update_time IS '更新时间';

-- ============================================
-- 3. Shopee 产品表
-- ============================================
CREATE TABLE shopee_product (
    id BIGSERIAL PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_name VARCHAR(500),
    item_sku VARCHAR(255),
    category_id BIGINT,
    status VARCHAR(50),
    description TEXT,
    item_short_desc TEXT,
    tags VARCHAR(1000),
    create_time_shopee TIMESTAMP,
    update_time_shopee TIMESTAMP,
    stock INT DEFAULT 0,
    sales INT DEFAULT 0,
    price DECIMAL(15, 2),
    original_price DECIMAL(15, 2),
    has_variation BOOLEAN DEFAULT FALSE,
    variation_count INT DEFAULT 0,
    image_count INT DEFAULT 0,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_shop FOREIGN KEY (shop_id) REFERENCES shopee_shop(id) ON DELETE CASCADE,
    CONSTRAINT uk_shopee_item_id UNIQUE (shop_id, item_id)
);

CREATE INDEX idx_shopee_product_shop_id ON shopee_product(shop_id);
CREATE INDEX idx_shopee_product_status ON shopee_product(status);
CREATE INDEX idx_shopee_product_create_time ON shopee_product(create_time_shopee);

-- 表注释
COMMENT ON TABLE shopee_product IS 'Shopee 产品表';
COMMENT ON COLUMN shopee_product.shop_id IS '关联的店铺 ID';
COMMENT ON COLUMN shopee_product.item_id IS 'Shopee 产品 ID';
COMMENT ON COLUMN shopee_product.item_name IS '产品名称';
COMMENT ON COLUMN shopee_product.item_sku IS '产品 SKU';
COMMENT ON COLUMN shopee_product.category_id IS '分类 ID';
COMMENT ON COLUMN shopee_product.status IS '状态: NORMAL/BANNED/DELETED/UNLIST';
COMMENT ON COLUMN shopee_product.description IS '产品描述';
COMMENT ON COLUMN shopee_product.item_short_desc IS '产品简短描述';
COMMENT ON COLUMN shopee_product.tags IS '产品标签';
COMMENT ON COLUMN shopee_product.create_time_shopee IS 'Shopee 创建时间';
COMMENT ON COLUMN shopee_product.update_time_shopee IS 'Shopee 更新时间';
COMMENT ON COLUMN shopee_product.stock IS '库存';
COMMENT ON COLUMN shopee_product.sales IS '销量';
COMMENT ON COLUMN shopee_product.price IS '价格';
COMMENT ON COLUMN shopee_product.original_price IS '原价';
COMMENT ON COLUMN shopee_product.has_variation IS '是否有变体';
COMMENT ON COLUMN shopee_product.variation_count IS '变体数量';
COMMENT ON COLUMN shopee_product.image_count IS '图片数量';
COMMENT ON COLUMN shopee_product.processed IS '是否已处理';
COMMENT ON COLUMN shopee_product.processed_time IS '处理时间';
COMMENT ON COLUMN shopee_product.create_time IS '创建时间';
COMMENT ON COLUMN shopee_product.update_time IS '更新时间';

-- ============================================
-- 4. Shopee 客户表
-- ============================================
CREATE TABLE shopee_customer (
    id BIGSERIAL PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    username VARCHAR(255),
    email VARCHAR(500),
    phone VARCHAR(100),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    orders_count INT DEFAULT 0,
    total_spent DECIMAL(15, 2) DEFAULT 0,
    average_order_value DECIMAL(15, 2),
    last_order_id BIGINT,
    last_order_sn VARCHAR(255),
    status VARCHAR(50),
    create_time_shopee TIMESTAMP,
    update_time_shopee TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer_shop FOREIGN KEY (shop_id) REFERENCES shopee_shop(id) ON DELETE CASCADE,
    CONSTRAINT uk_shopee_customer_id UNIQUE (shop_id, customer_id)
);

CREATE INDEX idx_shopee_customer_shop_id ON shopee_customer(shop_id);
CREATE INDEX idx_shopee_customer_email ON shopee_customer(email);
CREATE INDEX idx_shopee_customer_create_time ON shopee_customer(create_time_shopee);

-- 表注释
COMMENT ON TABLE shopee_customer IS 'Shopee 客户表';
COMMENT ON COLUMN shopee_customer.shop_id IS '关联的店铺 ID';
COMMENT ON COLUMN shopee_customer.customer_id IS 'Shopee 客户 ID';
COMMENT ON COLUMN shopee_customer.username IS '用户名';
COMMENT ON COLUMN shopee_customer.email IS '客户邮箱';
COMMENT ON COLUMN shopee_customer.phone IS '电话';
COMMENT ON COLUMN shopee_customer.first_name IS '名字';
COMMENT ON COLUMN shopee_customer.last_name IS '姓氏';
COMMENT ON COLUMN shopee_customer.orders_count IS '订单数量';
COMMENT ON COLUMN shopee_customer.total_spent IS '总消费金额';
COMMENT ON COLUMN shopee_customer.average_order_value IS '平均订单价值';
COMMENT ON COLUMN shopee_customer.last_order_id IS '最后订单 ID';
COMMENT ON COLUMN shopee_customer.last_order_sn IS '最后订单编号';
COMMENT ON COLUMN shopee_customer.status IS '状态: active/inactive';
COMMENT ON COLUMN shopee_customer.create_time_shopee IS 'Shopee 创建时间';
COMMENT ON COLUMN shopee_customer.update_time_shopee IS 'Shopee 更新时间';
COMMENT ON COLUMN shopee_customer.processed IS '是否已处理';
COMMENT ON COLUMN shopee_customer.processed_time IS '处理时间';
COMMENT ON COLUMN shopee_customer.create_time IS '创建时间';
COMMENT ON COLUMN shopee_customer.update_time IS '更新时间';

-- ============================================
-- 5. Shopee Webhook 日志表
-- ============================================
CREATE TABLE shopee_webhook_log (
    id BIGSERIAL PRIMARY KEY,
    shop_id BIGINT,
    webhook_type VARCHAR(255) NOT NULL,
    webhook_id BIGINT,
    resource_type VARCHAR(100),
    resource_id BIGINT,
    payload JSONB,
    headers JSONB,
    processed_status VARCHAR(50) DEFAULT 'pending',
    error_message TEXT,
    received_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_time TIMESTAMP,
    retry_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_webhook_shop FOREIGN KEY (shop_id) REFERENCES shopee_shop(id) ON DELETE SET NULL
);

CREATE INDEX idx_shopee_webhook_shop_id ON shopee_webhook_log(shop_id);
CREATE INDEX idx_shopee_webhook_type ON shopee_webhook_log(webhook_type);
CREATE INDEX idx_shopee_webhook_status ON shopee_webhook_log(processed_status);
CREATE INDEX idx_shopee_webhook_resource ON shopee_webhook_log(resource_type, resource_id);
CREATE INDEX idx_shopee_webhook_received_time ON shopee_webhook_log(received_time);

-- 表注释
COMMENT ON TABLE shopee_webhook_log IS 'Shopee Webhook 事件日志表';
COMMENT ON COLUMN shopee_webhook_log.shop_id IS '关联的店铺 ID';
COMMENT ON COLUMN shopee_webhook_log.webhook_type IS 'Webhook 类型';
COMMENT ON COLUMN shopee_webhook_log.webhook_id IS 'Shopee Webhook ID';
COMMENT ON COLUMN shopee_webhook_log.resource_type IS '资源类型: order/product/customer';
COMMENT ON COLUMN shopee_webhook_log.resource_id IS '资源 ID';
COMMENT ON COLUMN shopee_webhook_log.payload IS 'Webhook 负载';
COMMENT ON COLUMN shopee_webhook_log.headers IS '请求头';
COMMENT ON COLUMN shopee_webhook_log.processed_status IS '处理状态: pending/success/failed';
COMMENT ON COLUMN shopee_webhook_log.error_message IS '错误信息';
COMMENT ON COLUMN shopee_webhook_log.received_time IS '接收时间';
COMMENT ON COLUMN shopee_webhook_log.processed_time IS '处理时间';
COMMENT ON COLUMN shopee_webhook_log.retry_count IS '重试次数';
COMMENT ON COLUMN shopee_webhook_log.create_time IS '创建时间';

-- ============================================
-- 6. Shopee 同步日志表
-- ============================================
CREATE TABLE shopee_sync_log (
    id BIGSERIAL PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    sync_type VARCHAR(50) NOT NULL,
    sync_method VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    sync_status VARCHAR(50) DEFAULT 'running',
    total_count INT DEFAULT 0,
    success_count INT DEFAULT 0,
    failure_count INT DEFAULT 0,
    error_message TEXT,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
    duration BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sync_shop FOREIGN KEY (shop_id) REFERENCES shopee_shop(id) ON DELETE CASCADE
);

CREATE INDEX idx_shopee_sync_shop_id ON shopee_sync_log(shop_id);
CREATE INDEX idx_shopee_sync_type ON shopee_sync_log(sync_type);
CREATE INDEX idx_shopee_sync_status ON shopee_sync_log(sync_status);
CREATE INDEX idx_shopee_sync_start_time ON shopee_sync_log(start_time);

-- 表注释
COMMENT ON TABLE shopee_sync_log IS 'Shopee 同步日志表';
COMMENT ON COLUMN shopee_sync_log.shop_id IS '关联的店铺 ID';
COMMENT ON COLUMN shopee_sync_log.sync_type IS '同步类型: order/product/customer/full';
COMMENT ON COLUMN shopee_sync_log.sync_method IS '同步方式: scheduled/manual/webhook';
COMMENT ON COLUMN shopee_sync_log.start_date IS '开始日期';
COMMENT ON COLUMN shopee_sync_log.end_date IS '结束日期';
COMMENT ON COLUMN shopee_sync_log.sync_status IS '同步状态: running/success/failed';
COMMENT ON COLUMN shopee_sync_log.total_count IS '总记录数';
COMMENT ON COLUMN shopee_sync_log.success_count IS '成功数量';
COMMENT ON COLUMN shopee_sync_log.failure_count IS '失败数量';
COMMENT ON COLUMN shopee_sync_log.error_message IS '错误信息';
COMMENT ON COLUMN shopee_sync_log.start_time IS '开始时间';
COMMENT ON COLUMN shopee_sync_log.end_time IS '结束时间';
COMMENT ON COLUMN shopee_sync_log.duration IS '耗时（毫秒）';
COMMENT ON COLUMN shopee_sync_log.create_time IS '创建时间';

-- ============================================
-- 初始化数据
-- ============================================

-- 创建示例店铺配置（开发环境）
-- INSERT INTO shopee_shop (shop_id, shop_name, access_token, partner_id, partner_key, enabled, create_by)
-- VALUES (123456789, 'Demo Shop', 'your-access-token-here', 987654321, 'your-partner-key-here', TRUE, 'system');
