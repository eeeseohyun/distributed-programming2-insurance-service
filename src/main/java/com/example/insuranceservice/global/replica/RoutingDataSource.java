package com.example.insuranceservice.global.replica;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private DataSource writeDataSource;
    private DataSource readDataSource;
    private String writeDatabaseUrl;
    private String readDatabaseUrl;
    private boolean isInitialized = false;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.writeDataSource = (DataSource) targetDataSources.get("write");
        this.readDataSource = (DataSource) targetDataSources.get("read");
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (!isInitialized) {
            cacheDatabaseUrls();
            isInitialized = true;
        }

        String currentKey = DataSourceRoutingContext.getDataSourceType();

        if (currentKey == null || currentKey.equals("write")) {
            System.out.println("### Using WRITE DataSource (Main DB)");
            System.out.println("### Current Database URL: " + writeDatabaseUrl);
        } else if (currentKey.equals("read")) {
            System.out.println("### Using READ DataSource (Replica DB)");
            System.out.println("### Current Database URL: " + readDatabaseUrl);
        }

        return currentKey != null ? currentKey : "write";
    }

    private void cacheDatabaseUrls() {
        writeDatabaseUrl = getDatabaseUrl(writeDataSource, "write");
        readDatabaseUrl = getDatabaseUrl(readDataSource, "read");
    }

    private String getDatabaseUrl(DataSource dataSource, String type) {
        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection()) {
                return connection.getMetaData().getURL();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "### " + type + " DataSource is not initialized!";
    }
}
