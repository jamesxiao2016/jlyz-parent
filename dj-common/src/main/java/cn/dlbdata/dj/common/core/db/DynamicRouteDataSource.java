package cn.dlbdata.dj.common.core.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRouteDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSource();
	}

}
