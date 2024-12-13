package com.example.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности веб-приложения.
 * <p>
 * Этот класс конфигурирует безопасность веб-приложения, определяя, как обрабатываются запросы,
 * как осуществляется аутентификация и какие страницы защищены.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Создает и возвращает объект {@link PasswordEncoder} для шифрования паролей.
     *
     * @return BCryptPasswordEncoder для шифрования паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используется BCrypt для хэширования паролей
    }

    /**
     * Конфигурирует безопасность HTTP-запросов.
     * <p>
     * Здесь настраиваются разрешения для разных URL, а также параметры сессии и аутентификации.
     * </p>
     *
     * @param http объект HttpSecurity для конфигурации безопасности.
     * @return объект SecurityFilterChain, который содержит конфигурацию безопасности.
     * @throws Exception выбрасывается в случае ошибок в конфигурации.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // Начало конфигурации авторизации запросов
                .requestMatchers("/login", "/register", "/home", "/blog", "/blog/{id}").permitAll()  // Разрешает доступ без аутентификации на эти страницы
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Страницы с префиксом /admin/ доступны только для пользователей с ролью "ADMIN"
                .requestMatchers("/api/**").permitAll()  // Все запросы к API разрешены без аутентификации
                .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                .and()
                .formLogin() // Настройка формы для входа
                .loginPage("/login")  // Указываем страницу для входа
                .permitAll()  // Разрешаем доступ ко всем пользователям
                .defaultSuccessUrl("/", true)  // Страница, на которую пользователь будет перенаправлен после успешного входа
                .and()
                .logout() // Настройка выхода из системы
                .permitAll()  // Разрешаем доступ ко всем пользователям для выхода
                .logoutSuccessUrl("/login")  // Страница для редиректа после успешного выхода
                .and()
                .sessionManagement()  // Управление сессиями
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Сессия будет создана только если это необходимо
                .invalidSessionUrl("/login")  // Страница для редиректа в случае невалидной сессии
                .maximumSessions(1)  // Ограничение на количество активных сессий для одного пользователя
                .maxSessionsPreventsLogin(true);  // Запрещаем повторный вход, если уже есть активная сессия

        return http.build();  // Создание конфигурации безопасности и возврат объекта
    }
}
