package org.grizz.keeper.springconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created by Grizz on 2015-07-26.
 */
@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AuthenticationProvider restAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.csrf().disable()
                .addFilterBefore(filter, CsrfFilter.class)
                .authorizeRequests()
                .anyRequest().anonymous()
                .and()
                .httpBasic()
                .authenticationEntryPoint(null)
                .and()
                .formLogin()
                .successHandler((rq, rs, auth) -> log.info(auth.getName() + " has logged in..."))
                .failureHandler(null)
                .and()
                .logout()
                .addLogoutHandler((rq, rs, auth) -> log.info(auth.getName() + " has logged out..."))
                .logoutUrl("/logout");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(restAuthenticationProvider);
    }
}