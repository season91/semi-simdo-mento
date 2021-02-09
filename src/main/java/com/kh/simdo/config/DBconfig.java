package com.kh.simdo.config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
//원하는 객체를 따로 올릴수있게 된다.
public class DBconfig {
    // 컨텍스트에 올라간다.
    @Bean
    public OracleDataSource oracleDataSource() throws SQLException, IOException {
        OracleDataSource dataSource = new OracleDataSource();
        // Wallet_BM의 경로를 구하기 위해 클래스가 컴파일 되어 저장되는 경로를 구한다.
        // '/'로 시작하기 때문에 제일 첫 '/'를 제거 해준다.
        String walletPath = "/Users/choayoung91/Documents/CODE/semi-mento/simdo/src/main/resources/Wallet_simdoDB";
        System.out.println("////////////////////////////////////////////"+walletPath);
        dataSource.setURL("jdbc:oracle:thin:@simdodb_high?TNS_ADMIN="+walletPath);
        Properties prop = new Properties();
        prop.setProperty("user", "admin");
        prop.setProperty("password", "Simdodatabase4");
        dataSource.setConnectionProperties(prop);
        return dataSource;
    }

}
