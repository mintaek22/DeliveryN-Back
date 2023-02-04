package com.deliveryN.server.config;

import com.deliveryN.server.exception.CustomAccessDeniedHandler;
import com.deliveryN.server.exception.CustomAuthenticationEntryPoint;
import com.deliveryN.server.Jwt.JwtFilter;
import com.deliveryN.server.Jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final CustomAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //userdetailservice를 사용한다
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // 세션을 사용하지 않기 때문에 STATELESS로 설정
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

       //예외처리
    //    http.exceptionHandling()
      //      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
         //   .accessDeniedHandler(jwtAccessDeniedHandler);


        http.authorizeHttpRequests()
            .antMatchers("/user/login*").permitAll()
            .antMatchers("/user/signup").permitAll()
                .antMatchers("/oauth").permitAll()
            .anyRequest().permitAll();

        return http.build();
    }
}