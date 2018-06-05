package cn.dlbdata.dj.thirdparty.mp.sdk.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class LocalCache {
	public final static Cache<String, String> TOKEN_CACHE = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(10)
			// 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
			.concurrencyLevel(5)
			// 设置cache中的数据在写入之后的存活时间为10秒
			.expireAfterWrite(3600, TimeUnit.SECONDS)
			// 构建cache实例
			.build();
	public final static Cache<String, String> TICKET_CACHE = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(10)
			// 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
			.concurrencyLevel(5)
			// 设置cache中的数据在写入之后的存活时间为10秒
			.expireAfterWrite(60, TimeUnit.MINUTES)
			// 构建cache实例
			.build();
}
