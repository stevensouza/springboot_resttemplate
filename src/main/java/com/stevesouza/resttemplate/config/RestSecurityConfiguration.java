package com.stevesouza.resttemplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * This section defines the user accounts which can be used for
     * authentication as well as the roles each user has.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("joe").password("souza").roles("USER").and()
                .withUser("steve").password("souza").roles("USER", "ADMIN").and()
                .withUser("admin").password("admin").roles("ADMIN").and()
                .withUser("user").password("user").roles("USER");
    }

    /**
     * This section defines the security policy for the app.
     * - BASIC authentication is supported (enough for this REST-based demo)
     * - CSRF headers can be disabled if we are only creating a REST interface,
     *   not a web one.
     *
     *   Note if you don't provide the following the default is to protect all pages to have a login.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // .authenticated() - any user that is logged in can access. if not logged in returns 401 Not authenticated
        // .formLogin() - accessing any url triggers an automatic login page.  also a localhost:8080/logout page is provided

        http
                .formLogin().and()
                //.httpBasic().and()
                .authorizeRequests()
                    .anyRequest().authenticated().and()
         // note unless crsf is disabled postman and rest calls returned a 403 error Forbidden. I need to look into the
         // further implications of this.
        .csrf().disable();
//                .antMatchers(HttpMethod.POST, "/mydbentity").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/mydbentity/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/mydbentity/**").hasRole("ADMIN");
    }



}