package com.digicu.zuulserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (req, rsp, error) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, error.getMessage()))
                .and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtConfig),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/actuator/**").permitAll()
                .antMatchers("/authentication/**").permitAll()
                .antMatchers("/users/social/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers("/pos/**").hasRole("COMPANY")
                .antMatchers("/coupon/**").hasAnyRole("ADMIN", "COMPANY", "SOCIAL") // TEST위해 COMAPY함 나중에 COUSTOMER등으로 바꿔야함
                .anyRequest().authenticated()
                .and().cors()
                .and();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
