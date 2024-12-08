package com.example.insuranceservice.global.replica;

import org.springframework.stereotype.Component;

@Component
public class DataSourceRoutingContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public void setDataSourceType(String dataSourceType) {
        CONTEXT.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return CONTEXT.get();
    }

    public void clearDataSourceType() {
        CONTEXT.remove();
    }
}
