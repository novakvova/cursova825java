package epicentr.config;

import javax.sql.DataSource;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import epicentr.filters.JWTAuthenticationFilter;
import epicentr.filters.JWTAuthorizationFilter;
import epicentr.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static epicentr.constraint.SecurityConstants.SIGN_UP_URL;

//@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService customUserDetailsService;
//	@Value(value = "${auth0.apiAudience}")
//	private String apiAudience;
//	@Value(value = "${auth0.issuer}")
//	private String issuer;
	@Autowired
	private DataSource dataSource;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.userDetailsService(customUserDetailsService)
        	.passwordEncoder(passwordEncoder());
    }
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//		http.cors().and().csrf().disable().authorizeRequests()
//				.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
//
//				.antMatchers("/resources/**", "/webjars/**", "/lib/**","/assets/**").permitAll()
//				.antMatchers("/").permitAll()
//				.antMatchers("/api/v1/auth/**").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .failureUrl("/login?error")
//                .permitAll()
//                .and()
//				//.addFilter(new JWTAuthenticationFilter(authenticationManager()))
//				//.addFilter(new JWTAuthorizationFilter(authenticationManager()))
//				// this disables session creation on Spring Security
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
        	.headers()
        		.frameOptions().sameOrigin()
        		.and()
            .authorizeRequests()
            	.antMatchers("/resources/**", "/webjars/**", "/lib/**","/assets/**").permitAll()
                .antMatchers("/").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/forgotpassword").permitAll()
				.antMatchers("/changepassword").permitAll()
				.antMatchers("/product-view/**").permitAll()
				.antMatchers("/order").permitAll()
				.antMatchers("/files/**").permitAll()

//				.antMatchers("/lib/js/vue/vue.min.js").permitAll()
//				.antMatchers("/lib/js/vue/vue-router.min.js").permitAll()
//				.antMatchers("/lib/js/vue/vuetify.js").permitAll()
				.antMatchers("/forgot.js").permitAll()
//				.antMatchers("/lib/css/bootstrap.min.css").permitAll()
//				.antMatchers("/lib/css/vue/vuetify.css").permitAll()
				.antMatchers("/product-view/1").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/api/v1/**").permitAll()
				.antMatchers("/api/v1/products/**").permitAll()
				.antMatchers("/products").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
            	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            	.logoutSuccessUrl("/login?logout")
            	.deleteCookies("my-remember-me-cookie")
                .permitAll()
                .and()
             .rememberMe()
             	//.key("my-secure-key")
             	.rememberMeCookieName("my-remember-me-cookie")
             	.tokenRepository(persistentTokenRepository())
             	.tokenValiditySeconds(24 * 60 * 60)
             	.and()
            .exceptionHandling()
             	;
		http.csrf().disable();
		// Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//		JwtWebSecurityConfigurer
//				.forRS256("/api/v1/", "/")
//				.configure(http)
//				.cors().and().csrf().disable().authorizeRequests()
//				.anyRequest().permitAll();
    }
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
    PersistentTokenRepository persistentTokenRepository(){
    	JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
    	tokenRepositoryImpl.setDataSource(dataSource);
    	return tokenRepositoryImpl;
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
