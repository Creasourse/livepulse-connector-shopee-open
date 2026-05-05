package com.cs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Shopee 连接器配置
 *
 * @author Livepulse
 * @since 2.0
 */
@Configuration
@ConfigurationProperties(prefix = "shopee")
public class ShopeeConfig {

    /**
     * API 配置
     */
    private ApiConfig api = new ApiConfig();

    /**
     * 同步配置
     */
    private SyncConfig sync = new SyncConfig();

    /**
     * 调度任务配置
     */
    private ScheduledConfig scheduled = new ScheduledConfig();

    public static class ApiConfig {
        /**
         * API 版本
         */
        private String version = "2.0";

        /**
         * 请求超时时间（秒）
         */
        private int timeout = 30;

        /**
         * 连接超时时间（秒）
         */
        private int connectTimeout = 10;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }
    }

    public static class SyncConfig {
        /**
         * 批次大小
         */
        private int batchSize = 100;

        /**
         * 最大重试次数
         */
        private int maxRetries = 3;

        /**
         * 重试延迟（毫秒）
         */
        private long retryDelay = 1000;

        /**
         * 每次查询的最大天数
         */
        private int maxQueryDays = 30;

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public long getRetryDelay() {
            return retryDelay;
        }

        public void setRetryDelay(long retryDelay) {
            this.retryDelay = retryDelay;
        }

        public int getMaxQueryDays() {
            return maxQueryDays;
        }

        public void setMaxQueryDays(int maxQueryDays) {
            this.maxQueryDays = maxQueryDays;
        }
    }

    public static class ScheduledConfig {
        /**
         * 是否启用调度任务
         */
        private boolean enabled = true;

        /**
         * 订单同步配置
         */
        private TaskSyncConfig orderSync = new TaskSyncConfig("0 0 */4 * * ?", 60, 14400);

        /**
         * 产品同步配置
         */
        private TaskSyncConfig productSync = new TaskSyncConfig("0 0 2 * * ?", 120, 86400);

        /**
         * 客户同步配置
         */
        private TaskSyncConfig customerSync = new TaskSyncConfig("0 0 3 * * ?", 180, 86400);

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public TaskSyncConfig getOrderSync() {
            return orderSync;
        }

        public void setOrderSync(TaskSyncConfig orderSync) {
            this.orderSync = orderSync;
        }

        public TaskSyncConfig getProductSync() {
            return productSync;
        }

        public void setProductSync(TaskSyncConfig productSync) {
            this.productSync = productSync;
        }

        public TaskSyncConfig getCustomerSync() {
            return customerSync;
        }

        public void setCustomerSync(TaskSyncConfig customerSync) {
            this.customerSync = customerSync;
        }

        public static class TaskSyncConfig {
            /**
             * Cron 表达式
             */
            private String cron;

            /**
             * 初始延迟（秒）
             */
            private int initialDelay;

            /**
             * 固定延迟（秒）
             */
            private int fixedDelay;

            /**
             * 是否启用
             */
            private boolean enabled = true;

            public TaskSyncConfig() {
            }

            public TaskSyncConfig(String cron, int initialDelay, int fixedDelay) {
                this.cron = cron;
                this.initialDelay = initialDelay;
                this.fixedDelay = fixedDelay;
            }

            public String getCron() {
                return cron;
            }

            public void setCron(String cron) {
                this.cron = cron;
            }

            public int getInitialDelay() {
                return initialDelay;
            }

            public void setInitialDelay(int initialDelay) {
                this.initialDelay = initialDelay;
            }

            public int getFixedDelay() {
                return fixedDelay;
            }

            public void setFixedDelay(int fixedDelay) {
                this.fixedDelay = fixedDelay;
            }

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }

    public ApiConfig getApi() {
        return api;
    }

    public void setApi(ApiConfig api) {
        this.api = api;
    }

    public SyncConfig getSync() {
        return sync;
    }

    public void setSync(SyncConfig sync) {
        this.sync = sync;
    }

    public ScheduledConfig getScheduled() {
        return scheduled;
    }

    public void setScheduled(ScheduledConfig scheduled) {
        this.scheduled = scheduled;
    }
}
