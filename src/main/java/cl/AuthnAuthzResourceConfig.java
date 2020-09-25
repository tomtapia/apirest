package cl;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class AuthnAuthzResourceConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/courses**").authenticated()
			.antMatchers("/students**").authenticated()
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.logout().disable();
	}

}
