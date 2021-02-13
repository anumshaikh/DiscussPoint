package com.anums.blogApp.DiscussPoint.Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
public class ConfigureSpringSecutrity extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource datasource;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(datasource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery("select username,password,true as enabled from user where username=?")
                .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM user WHERE username=?");
        
        /*
         * this will go as we will keep the user info in db .withDefaultSchema()
         * .withUser("anum").password("anum123")
         * .roles("admin").and().withUser("joe").password("joe123").roles("user");
         */

        /*
         * TO STORE different schema .usersbyusernamequery(select
         * username,password,enabled from users where username = ?)
         * .authoritiesbyusernamequery(select username,authority where username = ?)
         */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/perform-registration").permitAll()
        .antMatchers("/postpage").hasAnyRole("USER", "ADMIN")
        .antMatchers("/homepage").hasRole("USER")
      
        
        .antMatchers("/resources/**").permitAll()
        .anyRequest().authenticated()
        
        .and().csrf().disable().formLogin().loginPage("/login") .loginProcessingUrl("/perform_login")
        .defaultSuccessUrl("/homepage", true).failureUrl("/login?error=true")
        .failureHandler(authenticationFailureHandler())
        .and().logout().logoutUrl("/logout")
        .deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler());
    }
/* 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    } */

    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailure();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
  /*    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    } */
}


