package com.acs.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@SuppressWarnings("deprecation")
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().disable();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable().addHeaderWriter(
                new XFrameOptionsHeaderWriter(
                        new WhiteListedAllowFromStrategy(Arrays.asList("https://www.naver.com","https://10.85.203.94:55756","https://www.google.com","https://www.daum.net/"))
                )
        );
		httpSecurity.headers().frameOptions().sameOrigin();
		httpSecurity.authorizeRequests().antMatchers("/**").permitAll();
		httpSecurity.logout().logoutUrl("/logout").invalidateHttpSession(true);
		// @formatter:off
		httpSecurity
	        .formLogin().loginPage("/login").permitAll()
	        .and()
	        .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/logout", "/oauth/revoke-token")
	        .and()
	        .authorizeRequests()
	        .anyRequest().authenticated()
	        // @formatter:on
	        .and()
	        .anonymous().disable()
	        .exceptionHandling()
	        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
	        .and()
	        .csrf()
	        .ignoringAntMatchers("/logout");
	    

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.httpFirewall(defaultHttpFirewall());
	}
	
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	}

  
}
