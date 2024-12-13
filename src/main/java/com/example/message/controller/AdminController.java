package com.example.message.controller;

import com.example.message.model.Role;
import com.example.message.model.User;
import com.example.message.repository.RoleRepository;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Контроллер для администрирования пользователей и их ролей.
 * <p>
 * Этот контроллер управляет действиями администратора в приложении, такими как управление пользователями,
 * редактирование их ролей и доступ к списку пользователей.
 * </p>
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    /**
     * Отображение списка пользователей для администратора.
     * <p>
     * Этот метод получает список всех пользователей и отображает его на странице управления пользователями.
     * Доступ только для пользователей с ролью "ADMIN".
     * </p>
     *
     * @param model объект для добавления атрибутов на страницу.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @return имя представления для отображения.
     */
    @GetMapping("/users")
    public String manageUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }
        model.addAttribute("users", userService.getAllUsers()); // Добавляем список пользователей в модель
        return "manage_users"; // Возвращаем имя представления
    }

    /**
     * Редактирование информации о пользователе.
     * <p>
     * Этот метод предоставляет форму для редактирования пользователя, где администратор может изменить его роль.
     * </p>
     *
     * @param id ID пользователя, которого необходимо отредактировать.
     * @param model объект для добавления атрибутов на страницу.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @return имя представления для отображения.
     */
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }
        User user = userService.findById(id); // Получаем пользователя по ID
        if (user == null) {
            return "redirect:/admin/users?error=UserNotFound"; // Если пользователь не найден, возвращаем ошибку
        }
        model.addAttribute("user", user); // Добавляем информацию о пользователе в модель
        model.addAttribute("roles", roleRepository.findAll()); // Добавляем список всех ролей в модель
        return "edit_user"; // Возвращаем имя представления для редактирования пользователя
    }

    /**
     * Обновление информации о пользователе, включая его роль.
     * <p>
     * Этот метод обрабатывает POST-запросы для обновления пользователя.
     * </p>
     *
     * @param id ID пользователя, которого нужно обновить.
     * @param roleName новая роль пользователя.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @return перенаправление на страницу управления пользователями.
     */
    @PostMapping("/users/update")
    public String updateUser(@RequestParam("id") Long id, @RequestParam("role") String roleName, @AuthenticationPrincipal UserDetails userDetails) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }
        User user = userService.findById(id); // Получаем пользователя по ID
        if (user == null) {
            return "redirect:/admin/users?error=UserNotFound"; // Если пользователь не найден, возвращаем ошибку
        }
        Role role = roleRepository.findByName(roleName); // Получаем роль по имени
        if (role != null) {
            Set<Role> newRoles = new HashSet<>();
            newRoles.add(role); // Устанавливаем новую роль для пользователя
            user.setRoles(newRoles); // Присваиваем роли пользователю
            userService.updateUser(user); // Обновляем пользователя в базе данных
        }
        return "redirect:/admin/users"; // Перенаправляем на страницу управления пользователями
    }

    /**
     * Проверка, имеет ли пользователь определенную роль.
     *
     * @param username имя пользователя.
     * @param roleName роль, которую нужно проверить.
     * @return true, если у пользователя есть указанная роль, иначе false.
     */
    private boolean hasRole(String username, String roleName) {
        User user = userService.findByUsername(username); // Получаем пользователя по имени
        if (user == null || user.getRoles() == null) {
            return false;
        }
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleName)); // Проверяем, есть ли у пользователя нужная роль
    }
}
