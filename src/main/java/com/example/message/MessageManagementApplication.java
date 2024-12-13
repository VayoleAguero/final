package com.example.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для запуска Spring Boot приложения.
 * <p>
 * Этот класс запускает приложение с использованием Spring Boot и основной конфигурации.
 * Аннотация {@link SpringBootApplication} активирует автоматическую настройку,
 * сканирование компонентов и настройку конфигурации.
 * </p>
 *
 * <p>
 * Основной метод запускает Spring Boot приложение, передавая в качестве аргументов
 * командной строки параметры, полученные из метода {@code main}.
 * </p>
 *
 * @see SpringApplication#run(Class, String...)
 */
@SpringBootApplication
public class MessageManagementApplication {

    /**
     * Метод {@code main} запускает приложение.
     * <p>
     * Этот метод используется для запуска Spring Boot приложения. Он вызывает
     * {@link SpringApplication#run(Class, String...)} для запуска всего приложения.
     * </p>
     *
     * @param args Аргументы командной строки, которые могут быть переданы в приложение.
     */
    public static void main(String[] args) {
        SpringApplication.run(MessageManagementApplication.class, args);
    }
}
