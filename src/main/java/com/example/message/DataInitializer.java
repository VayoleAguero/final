package com.example.message;

import com.example.message.model.Role;
import com.example.message.model.User;
import com.example.message.repository.RoleRepository;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Класс, реализующий {@link CommandLineRunner} для инициализации данных при запуске приложения.
 * <p>
 * Этот компонент выполняется при старте приложения и выполняет следующие действия:
 * 1. Создаёт роли, если они ещё не существуют в базе данных.
 * 2. Создаёт пользователя с ролью "ADMIN" (root), если такого пользователя ещё нет.
 * </p>
 *
 * <p>
 * С помощью аннотации {@link Component} Spring автоматически регистрирует этот класс как компонент
 * в контексте приложения, а метод {@link #run(String... args)} будет выполнен при старте приложения.
 * </p>
 *
 * @see CommandLineRunner#run(String...)
 * @see UserService
 * @see RoleRepository
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Метод, выполняемый при старте приложения для инициализации данных.
     * <p>
     * В этом методе создаются три роли (ADMIN, VIEWER, EDITOR), если они не существуют в базе данных,
     * и проверяется наличие пользователя с именем "root". Если такого пользователя нет, он создаётся с
     * ролью "ADMIN".
     * </p>
     *
     * @param args Аргументы командной строки, которые могут быть переданы при запуске приложения.
     */
    @Override
    public void run(String... args) throws Exception {
        // Создаём роли, если их нет
        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
        }

        Role viewerRole = roleRepository.findByName("VIEWER");
        if (viewerRole == null) {
            viewerRole = new Role("VIEWER");
            roleRepository.save(viewerRole);
        }

        Role editorRole = roleRepository.findByName("EDITOR");
        if (editorRole == null) {
            editorRole = new Role("EDITOR");
            roleRepository.save(editorRole);
        }

        // Проверяем, существует ли пользователь root
        if (userService.findByUsername("root") == null) {
            User adminUser = new User();
            adminUser.setUsername("root");
            adminUser.setPassword("admin"); // Пароль будет захеширован в saveNewUser
            adminUser.getRoles().add(adminRole); // Добавляем роль ADMIN
            userService.saveNewUser(adminUser);
            System.out.println("Администратор 'root' создан.");
        }
    }
}
