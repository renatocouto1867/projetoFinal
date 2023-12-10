package br.edu.ifto.projetofinal.model.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    UsuarioDetailsConfig usuarioDetailsConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        customizer ->
                                customizer
                                        .requestMatchers("/resources/**", "/static/**", "/css/**",
                                                "/js/**", "/images/**", "/status/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "paciente/form").permitAll()
                                        .requestMatchers(HttpMethod.POST, "paciente/save").permitAll()

                                        .requestMatchers(HttpMethod.POST,"/paciente/**","/medico/listnome","http://localhost:8080/medico/listnome", "/medico/**", "/consulta/**",
                                                "/pacientes/**", "/consultas/**","/agendamento/**").hasAnyRole("ADMIN","MEDICO")
                                        .requestMatchers("/paciente/**", "/medico/**", "/consulta/**",
                                                "/pacientes/**", "/consultas/**","/agendamento/**").hasAnyRole("ADMIN","MEDICO")


                                        .anyRequest() //define que a configuração é válida para qualquer requisição.
                                        .authenticated() //define que o usuário precisa estar autenticado.
                )
                .formLogin(customizer ->
                        customizer
                                .loginPage("/login")
                                .defaultSuccessUrl("/home", true)
                                .permitAll()
                                .successHandler((request, response, authentication) -> {
                                    authentication.getAuthorities().forEach(authority -> {
                                        System.out.println("Role: " + authority.getAuthority());
                                    });
                                    response.sendRedirect("/home");
                                })

                )
                 .httpBasic(withDefaults()) // Configuração para autenticação básica (usuário e senha)
                .logout(LogoutConfigurer::permitAll)
                .rememberMe(withDefaults());


        return http.build();
    }

    @Autowired
    public void configureUserDetails(final AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(usuarioDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


