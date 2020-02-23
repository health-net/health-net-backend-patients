package org.healthnet.backend.patients.infrastructure.persistence.tools;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Map;

public class PatientsDataSource extends MysqlDataSource {
    public PatientsDataSource() {

        Map<String, String> env = System.getenv();
        setDatabaseName(env.getOrDefault("DB_NAME", "healthnet_patients"));
        setUser(env.getOrDefault("DB_USER", "root"));
        setPassword(env.getOrDefault("DB_PASSWORD", "root"));
        setURL(env.getOrDefault("DB_URL", "bruh"));
        setPort(Integer.parseInt(env.getOrDefault("DB_PORT", "3306")));
    }
}
