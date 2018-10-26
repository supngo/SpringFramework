package com.example.demo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;

@SpringBootApplication
// @EnableOAuth2Sso
@EnableOAuth2Client
@RestController
public class SpringOauthApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringOauthApplication.class, args);
	}

	@RequestMapping("/user")
  public Principal user(Principal principal) {
    return principal;
	}
	
	@Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .antMatcher("/**")
      .authorizeRequests()
        .antMatchers("/", "/login**", "/webjars/**", "/error**")
        .permitAll()
      .anyRequest()
				.authenticated()
			.and().logout().logoutSuccessUrl("/").permitAll()
			.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}
	
	// Duplicate Fitler Resources *****************************************
	// private Filter ssoFilter() {
	// 	CompositeFilter filter = new CompositeFilter();
	// 	List<Filter> filters = new ArrayList<>();
	
	// 	// Facebook Oauth filter
	// 	OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
	// 	OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
	// 	facebookFilter.setRestTemplate(facebookTemplate);
	// 	UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
	// 	tokenServices.setRestTemplate(facebookTemplate);
	// 	facebookFilter.setTokenServices(tokenServices);
	// 	filters.add(facebookFilter);

	// 	// Google Oauth filter
	// 	OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
	// 	OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
	// 	githubFilter.setRestTemplate(githubTemplate);
	// 	tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
	// 	tokenServices.setRestTemplate(githubTemplate);
	// 	githubFilter.setTokenServices(tokenServices);
	// 	filters.add(githubFilter);
  // 	filter.setFilters(filters);
		
	// 	return filter;
	// }

	// @Bean
  // @ConfigurationProperties("facebook.client")
  // public AuthorizationCodeResourceDetails facebook() {
  //   return new AuthorizationCodeResourceDetails();
	// }
	
	// @Bean
  // @ConfigurationProperties("facebook.resource")
  // public ResourceServerProperties facebookResource() {
  //   return new ResourceServerProperties();
	// }

	// @Bean
	// @ConfigurationProperties("github.client")
	// public AuthorizationCodeResourceDetails github() {
	// 	return new AuthorizationCodeResourceDetails();
	// }

	// @Bean
	// @ConfigurationProperties("github.resource")
	// public ResourceServerProperties githubResource() {
	// 	return new ResourceServerProperties();
	// }
	// Duplicate Fitler Resources *****************************************

	
	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/login/facebook"));
		filters.add(ssoFilter(github(), "/login/github"));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		return filter;
	}

	class ClientResources {
		@NestedConfigurationProperty
		private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
	
		@NestedConfigurationProperty
		private ResourceServerProperties resource = new ResourceServerProperties();
	
		public AuthorizationCodeResourceDetails getClient() {
			return client;
		}
	
		public ResourceServerProperties getResource() {
			return resource;
		}
	}

	@Bean
	@ConfigurationProperties("github")
	public ClientResources github() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook() {
		return new ClientResources();
	}
	
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
}
