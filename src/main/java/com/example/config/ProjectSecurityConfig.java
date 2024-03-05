package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(handlerMappingIntrospector);

        http.csrf((csrf) -> csrf.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/saveMsg"))
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/public/**"))
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/api/**"))
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/data-api/**"))
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/eazyschool/actuator/**")))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvcMatcherBuilder.pattern("/dashboard")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayMessages/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/closeMsg/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/eazyschool/actuator/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/data-api/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/student/**")).hasRole("STUDENT")
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayProfile")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/updateProfile")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/holidays/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/courses")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/contact")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/saveMsg")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/about")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/logout")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll())
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
