package com.upc.ecocycle.security.config;


import com.upc.ecocycle.security.filters.JwtRequestFilter;
import com.upc.ecocycle.security.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    //Inyectando JWT Filter por constructor
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    //se define como un bean para que pueda ser utilizado en otros lugares, como en el controlador de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // Bean para codificar las contraseñas para ser usando en cualquier parte de la app
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //(2) Definir el SecurityFilterChain como un bean, ya no necesitamos heredar, configuramos toda la seg.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // deshabilitar CSRF ya que no es necesario para una API REST
                .cors(Customizer.withDefaults()) //esto es los de CORS q añadi
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/security/authenticate",
                                        "/ecocycle/vecino/registrar",
                                        "/ecocycle/evento/cantidadEventosLogrados",
                                        "/ecocycle/municipalidad/ranking",
                                        "/ecocycle/reciclaje/cantidadPorTipo",
                                        "/ecocycle/vecino/ranking",
                                        //estos de abajo hay q quitar dsps
                                        "/ecocycle/evento/listarYfiltrar",
                                        "/ecocycle/evento/registrar",
                                        "/ecocycle/evento/detalle/**",
                                        "/ecocycle/evento/eliminar/**",
                                        "/ecocycle/evento/modificar",
                                        "/ecocycle/vecino/buscarPorDNI",
                                        "/ecocycle/evento/listarPorVecino",
                                        "/ecocycle/exv/registrar",
                                        "/ecocycle/exv/modificar",
                                        "/ecocycle/exv/eliminar/**",
                                        "/ecocycle/exv/comentarios",
                                        "/ecocycle/exv/estadisticasVecinosPorEvento",
                                        "/ecocycle/exv/buscarPorEventoYVecino/**"
                                        ).permitAll()
                                //.requestMatchers("/api/proveedores").hasRole("ADMIN")
                                .anyRequest().authenticated() // cualquier endpoint puede ser llamado con tan solo autenticarse
                        //.anyRequest().denyAll() // aquí se obliga a todos los endpoints usen @PreAuthorize
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Añadir el filtro JWT antes del filtro de autenticación
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // ✅ origen Angular
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    //Filter opcional si se desea configurar globalmente el acceso a los endpoints sin anotaciones
    // en cada endpoint
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("${ip.frontend}");
//        config.addAllowedMethod("*");
//        config.addExposedHeader("Authorization");
//        source.registerCorsConfiguration("/**", config); //para todos los paths
//        return new CorsFilter(source);
//    }
}
