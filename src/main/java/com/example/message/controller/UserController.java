package com.example.message.controller;

import com.example.message.model.User;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер для управления действиями пользователей, включая регистрацию, логин, отображение информации о текущем пользователе.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Главная страница приложения, отображающая информацию о текущем пользователе.
     *
     * @param userDetails текущий пользователь, аутентифицированный в системе.
     * @param model модель для передачи данных в представление.
     * @return имя представления главной страницы.
     */
    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            // Находим пользователя по имени и передаем его в модель
            User user = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("currentUser", user);  // Добавляем текущего пользователя в модель
        }
        return "index";  // Возвращаем имя шаблона для главной страницы
    }

    /**
     * Страница для регистрации нового пользователя.
     *
     * @param model модель для передачи данных в представление.
     * @return имя представления страницы регистрации.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "register";  // Возвращаем имя шаблона для страницы регистрации
    }

    /**
     * Обработка данных из формы регистрации и создание нового пользователя.
     *
     * @param userForm объект, содержащий данные формы регистрации.
     * @param model модель для передачи данных в представление.
     * @return перенаправление на страницу логина или отображение ошибки.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userForm") User userForm, Model model) {
        // Проверяем, существует ли уже пользователь с таким именем
        if (userService.findByUsername(userForm.getUsername()) != null) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "register";  // Если пользователь существует, возвращаем на страницу регистрации с ошибкой
        }

        // Сохраняем нового пользователя
        userService.saveNewUser(userForm);
        return "redirect:/login";  // После регистрации перенаправляем на страницу логина
    }

    /**
     * Страница для ввода логина.
     *
     * @return имя представления страницы логина.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Возвращаем имя шаблона для страницы логина
    }

    /**
     * Обработка логина пользователя.
     *
     * @return перенаправление на главную страницу после успешного логина.
     */
    @PostMapping("/login")
    public String loginUser() {
        return "redirect:/";  // После успешного логина перенаправляем на главную страницу
    }

    /**
     * Страница выхода из аккаунта.
     *
     * @return перенаправление на страницу логина после выхода.
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";  // После выхода перенаправляем на страницу логина
    }

    /**
     * Страница "О нас".
     *
     * @return имя представления страницы "О нас".
     */
    @GetMapping("/about")
    public String showAbout() {
        return "/about";  // Возвращаем имя шаблона для страницы "О нас"
    }
}
