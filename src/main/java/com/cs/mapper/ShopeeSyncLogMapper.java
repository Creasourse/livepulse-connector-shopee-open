package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.ShopeeSyncLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Shopee 同步日志 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface ShopeeSyncLogMapper extends BaseMapper<ShopeeSyncLog> {
}
