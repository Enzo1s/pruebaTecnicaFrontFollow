package com.api.vital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
            .authorizeRequests()
                .antMatchers("/oauth2/**",
                		"/api/auth/*",
                		"/mobile/*", 
                		"/css/*", 
                		"/js/*", 
                		"/img/*", 
                		"/recuperar", 
                		"/cambiar", 
                		"/login",
                		"/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/api/docs/**").permitAll()
                        .antMatchers("/*").authenticated()
                        .and().formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/loginsuccess")

                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll().and().csrf()
                .disable()
                .sessionManagement()
                .invalidSessionUrl("/login")
                .maximumSessions(2)
                .expiredUrl("/login");
    }
     
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addExposedHeader("Location");

        source.registerCorsConfiguration("/**", config);

        return source;
    }
    
    @Bean
	public JWTtokenFilter jwtTokenFilter() {
		return new JWTtokenFilter();
	}

}


