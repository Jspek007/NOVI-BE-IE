package johan.spekman.novibeie.module_security;

import johan.spekman.novibeie.module_security.filter.CustomAuthenticationFilter;
import johan.spekman.novibeie.module_security.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/v1/sales_orders/**").hasAnyAuthority("CUSTOMERSERVICE",
                "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/sales_orders/**").hasAnyAuthority("CUSTOMERSERVICE",
                "ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/v1/customers/**").hasAnyAuthority("CUSTOMERSERVICE", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/customers/**").hasAnyAuthority("CUSTOMERSERVICE", "ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/customers/**").hasAnyAuthority("CUSTOMERSERVICE", "ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/v1/customers/**").hasAnyAuthority("CUSTOMERSERVICE", "ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/v1/products/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/products/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/products/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/v1/products/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/v1/category/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/category/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/category/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/v1/category/**").hasAnyAuthority("PRODUCTMANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/v1/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/v1/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
