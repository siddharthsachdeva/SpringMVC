package com.sid.dashboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/assets/**","/ckeditor/**","/css/**","/images/**" ).permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
			.loginPage("/")
			.defaultSuccessUrl("/dashboard/")
				.permitAll()
			.and()
				
				 // For storing in cookie
				 .rememberMe()
				.key("uniqueAndSecret")
				//Giving random cookie name which can be visible on browser
				.rememberMeCookieName("rx101.205")
				.tokenValiditySeconds(24 * 60 * 60)
			
			
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")
				.permitAll();
				
       
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("****").password("****").roles("USER");
	}

}
