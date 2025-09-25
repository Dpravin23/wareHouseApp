package in.nit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {


    // Configure security filter chain (authorization rules)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            		
            		.requestMatchers("/send","/verify-otp","/verify","/user/login").permitAll()
            		/*.requestMatchers("/verify-otp").permitAll()
            		.requestMatchers("/verify").permitAll()
            		
            		.requestMatchers("/user/login").permitAll()*/
				.requestMatchers("/wareHouseDashboard").hasAuthority("EMPLOYEE")
				.requestMatchers("/shipmenttype").hasAuthority("EMPLOYEE")
				.requestMatchers("/home**").hasAuthority("ADMIN") // Only ADMIN can access
				 //admin // Publicly accessible
				 .anyRequest().authenticated() 
				/* .anyRequest().hasAnyAuthority("EMPLOYEE","ADMIN") */
				 //
                // All other requests require authentication
                
            )
            .formLogin(form -> form
            		.loginProcessingUrl("/login").permitAll()
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/setup", true) // Redirect to /home after successful login
                .failureUrl("/user/login?error")
            )
            .logout(logout -> logout
            	.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .logoutSuccessUrl("/user/login?logout") // Redirect to login page after logout
            );
        
        return http.build();
    }
   
}