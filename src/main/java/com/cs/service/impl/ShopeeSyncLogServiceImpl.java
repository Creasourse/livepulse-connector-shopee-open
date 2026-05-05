package com.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.ShopeeSyncLog;
import com.cs.mapper.ShopeeSyncLogMapper;
import com.cs.service.ShopeeSyncLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Shopee 同步日志服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class ShopeeSyncLogServiceImpl extends ServiceImpl<ShopeeSyncLogMapper, ShopeeSyncLog> implements ShopeeSyncLogService {

    private final ShopeeSyncLogMapper syncLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopeeSyncLog createSyncLog(Long shopId, String syncType, String syncMethod) {
        ShopeeSyncLog syncLog = new ShopeeSyncLog();
        syncLog.setShopId(shopId);
        syncLog.setSyncType(syncType);
        syncLog.setSyncMethod(syncMethod);
        syncLog.setSyncStatus("running");
        syncLog.setTotalCount(0);
        syncLog.setSuccessCount(0);
        syncLog.setFailureCount(0);
        syncLog.setStartTime(LocalDateTime.now());

        save(syncLog);
        return syncLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncLogSuccess(ShopeeSyncLog syncLog, int totalCount, int successCount, int failureCount) {
        syncLog.setTotalCount(totalCount);
        syncLog.setSuccessCount(successCount);
        syncLog.setFailureCount(failureCount);
        syncLog.setSyncStatus("success");
        syncLog.setEndTime(LocalDateTime.now());

        if (syncLog.getStartTime() != null) {
            Duration duration = Duration.between(syncLog.getStartTime(), syncLog.getEndTime());
            syncLog.setDuration(duration.toMillis());
        }

        return updateById(syncLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncLogFailure(ShopeeSyncLog syncLog, int totalCount, int successCount, int failureCount, String errorMessage) {
        syncLog.setTotalCount(totalCount);
        syncLog.setSuccessCount(successCount);
        syncLog.setFailureCount(failureCount);
        syncLog.setSyncStatus("failed");
        syncLog.setErrorMessage(errorMessage);
        syncLog.setEndTime(LocalDateTime.now());

        if (syncLog.getStartTime() != null) {
            Duration duration = Duration.between(syncLog.getStartTime(), syncLog.getEndTime());
            syncLog.setDuration(duration.toMillis());
        }

        return updateById(syncLog);
    }
}
