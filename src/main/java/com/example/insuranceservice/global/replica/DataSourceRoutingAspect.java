package com.example.insuranceservice.global.replica;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceRoutingAspect {

    @Autowired
    private DataSourceRoutingContext dataSourceRoutingContext;
    @Before("@annotation(ReadOnly)")
    public void setReadDataSource(JoinPoint joinPoint) {
        dataSourceRoutingContext.setDataSourceType("read");
        System.out.println("Using READ DataSource for method: " + joinPoint.getSignature());
    }
    @After("@annotation(ReadOnly)")
    public void clearDataSource(JoinPoint joinPoint) {
        dataSourceRoutingContext.clearDataSourceType();
        System.out.println("Cleared DataSource after method: " + joinPoint.getSignature());
    }
}
