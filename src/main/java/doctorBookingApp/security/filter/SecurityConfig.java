package doctorBookingApp.security.filter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter filter;


    @Bean
    public PasswordEncoder passwordEncoder(){
//       return NoOpPasswordEncoder.getInstance();
      return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/hallo/**").permitAll()
                        // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/users/**").hasAnyRole("PATIENT", "ADMIN") // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/users-authentication/**").permitAll() // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/auth-for-reset/**").hasAnyRole("PATIENT", "ADMIN") // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/timeslots/**").permitAll() // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/appointments/**").hasAnyRole("PATIENT", "ADMIN") // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/departments/**").hasAnyRole("PATIENT", "ADMIN") // добавила в контексте разделения функционала между ролями
                        .requestMatchers("/doctor-profiles/**").hasAnyRole("PATIENT", "ADMIN") // добавила в контексте разделения функционала между ролями

                        // .requestMatchers(HttpMethod.GET,"/auth/profile").authenticated()                        .
//                        .requestMatchers(HttpMethod.GET,"/departments/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/doctor-profiles/**").permitAll()//

                        //.anyRequest().permitAll())
                      .anyRequest().authenticated())


                //конфигурация формы входа. При входе пользователь будет перенаправлен на /timeslots.
                // НО ВОЗМОЖНО, ЧТО ЭТО ИЗЛИШНЕ И ФОРМУ СТОИТ СДЕЛАТЬ УНИВЕРСАЛЬНОЙ
                .formLogin(form -> form
                        .loginPage("/auth") // добавила для APPOINTMENT-a
                        .defaultSuccessUrl("/timeslots", true) // добавила для APPOINTMENT-a
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/timeslots") // добавила для APPOINTMENT-a
                        .permitAll())

                .exceptionHandling(ex -> ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
