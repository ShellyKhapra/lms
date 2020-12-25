package com.lms.datasource;



import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

public class DataSourceFactory {
    private static final ConcurrentHashMap<Configuration, DataSource> dataSources = new ConcurrentHashMap();

    private DataSourceFactory() {
    }

    public static HikariDataSource createDataSource(Configuration configuration) {
        return createDataSourceWithMetrics(configuration);
    }

    public static HikariDataSource createDataSourceWithMetrics(Configuration configuration) {
        return (HikariDataSource)dataSources.compute(configuration, (config, dataSource) -> {
            return forgeDataSource(config, (HikariDataSource)dataSource);
        });
    }

    private static HikariDataSource forgeDataSource(Configuration configuration, HikariDataSource current) {
        if (current != null && !current.isClosed()) {
            return current;
        } else {
            HikariConfig hikariConfig = new HikariConfig(configuration.getProperties());


            return new HikariDataSource(hikariConfig);
        }
    }
}

