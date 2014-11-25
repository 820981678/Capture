package com.icapture.init.cache.impl;

import com.icapture.init.cache.CacheService;

public abstract class CacheServiceBase implements CacheService {

	@Override
	public Class<?> getCl() {
		return this.getClass();
	}
	
}
