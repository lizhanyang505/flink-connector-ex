package com.github.aly8246.option;

import com.github.aly8246.common.BaseOption;
import com.github.aly8246.dialect.JdbcDialect;
import com.github.aly8246.dialect.JdbcDialectService;
import org.apache.flink.table.descriptors.DescriptorProperties;

import static com.github.aly8246.descriptor.JdbcDescriptor.*;

public class JdbcOption extends BaseOption {
    //jdbc支持认证 usernamePassword
    private String username;

    //数据库方言
    private JdbcDialect jdbcDialect;

    //数据库表
    private String table;

    //数据库驱动程序
    private String jdbcDriver;

    public JdbcOption(DescriptorProperties descriptorProperties) {
        super(descriptorProperties);

        //选择数据库方言
        descriptorProperties
                .getOptionalString(CONNECTOR_URL)
                .flatMap(JdbcDialectService::get)
                .ifPresent(this::setJdbcDialect);

        //从配置中获得驱动程序，如果没有就调用jdbc方言的默认驱动程序
        this.jdbcDriver = descriptorProperties.getOptionalString(CONNECTOR_DRIVER)
                .orElse(this.jdbcDialect.defaultDriverName().get());

        descriptorProperties.getOptionalString(CONNECTOR_USERNAME).ifPresent(this::setUsername);
        descriptorProperties.getOptionalString(CONNECTOR_TABLE).ifPresent(this::setTable);
    }

    public JdbcDialect getJdbcDialect() {
        return jdbcDialect;
    }

    public void setJdbcDialect(JdbcDialect jdbcDialect) {
        this.jdbcDialect = jdbcDialect;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
