package com.stevesouza.resttemplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * This section defines the user accounts which can be used for
     * authentication as well as the roles each user has.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* note the DelegatingPasswordEncoder encodes as bcrypt only, but can match to anything.
         * But the match must be passed in the id (unless a default matcher was defined) {bcrypt}
         * for example. More explicit to use BCryptPasswordEncoder for encoding
         */

        // used to encode steve password in bcrypt
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
               //.passwordEncoder(NoOpPasswordEncoder.getInstance())
                // note the password method takes the encoded password prefixed by the id
                // For example {noop}steve, or {bcrypt}$2a....
                .withUser("joe").password("{noop}joe").roles("USER").and()
                .withUser("steve").password(encoder.encode("steve")).roles("USER", "ADMIN").and()
                .withUser("admin").password("{noop}admin").roles("ADMIN").and()
                .withUser("user").password("{noop}user").roles("USER");
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
        //   note with rest api I wasn't able to return the proper login info

        http
                .formLogin().and() // form login uses 'username' and 'password' in the form submission by default
                .httpBasic().and()
                .logout().and()
                .authorizeRequests()
                    .anyRequest().authenticated().and()
         // note unless crsf is disabled postman and rest calls returned a 403 error Forbidden. I need to look into the
         // further implications of this.
        .csrf().disable();

        // allows you to use http://localhost:8080/h2-console
        // https://stackoverflow.com/questions/40165915/why-does-the-h2-console-in-spring-boot-show-a-blank-screen-after-logging-in
        http.headers().frameOptions().sameOrigin();
//                .antMatchers(HttpMethod.POST, "/mydbentity").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/mydbentity/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/mydbentity/**").hasRole("ADMIN");
    }





}