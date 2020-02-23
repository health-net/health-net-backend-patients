package org.healthnet.backend.patients.infrastructure.persistence.tools;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Map;

public class PatientsDataSource extends MysqlDataSource {
    public PatientsDataSource() {
        Map<String, String> env = System.getenv();
        String tmp = "jdbc:mysql://"+env.getOrDefault("DB_URL", "bruh")+":"+env.getOrDefault("DB_PORT", "3306")+
                "/"+env.getOrDefault("DB_NAME", "healthnet_patients")+"?user="+env.getOrDefault("DB_USER", "root")+"&password="+env.getOrDefault("DB_PASSWORD", "root");
        setURL(tmp);
    }
}
