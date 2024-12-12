package com.example.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/css/**", "/js/**").permitAll() // ログインページと静的リソースを許可
                .antMatchers("/tasks/**").authenticated() // /tasks へのアクセスには認証を必須
                .anyRequest().authenticated() // 他のリクエストも認証が必要
                .and()
                .formLogin()
                .loginPage("/login") // カスタムログインページ
                .defaultSuccessUrl("/tasks", true) // ログイン成功時の遷移先
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login") // ログアウト後の遷移先
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 平文のパスワード検証（簡易テスト用）
    }
}
