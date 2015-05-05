package com.zzy.dao;

import com.zzy.model.ShardInfo;

public interface ShardInfoDao {
	public ShardInfo findByAdId(Long adId);
}
