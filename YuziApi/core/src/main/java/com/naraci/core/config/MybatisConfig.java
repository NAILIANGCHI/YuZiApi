package com.naraci.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ShenZhaoYu
 * @date 2024/2/16
 */
@EnableTransactionManagement
@Configuration
public class MybatisConfig {
    /**
     * 注册mybatis-plus插件
     *
     * @return 注册器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 指定数据库类型可优化速度，避免每次查询数据库类型
        paginationInnerInterceptor.setDbType(DbType.MYSQL);

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
