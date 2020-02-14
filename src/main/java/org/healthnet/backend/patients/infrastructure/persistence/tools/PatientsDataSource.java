package org.healthnet.backend.patients.infrastructure.persistence.tools;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Map;

public class PatientsDataSource extends MysqlDataSource {
    public PatientsDataSource() {
        setDatabaseName("healthnet_patients");
        Map<String, String> env = System.getenv();
        setUser(env.getOrDefault("DB_USER", "root"));
        setPassword(env.getOrDefault("DB_PASSWORD", "root"));
    }
}
