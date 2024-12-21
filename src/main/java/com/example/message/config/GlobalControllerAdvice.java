package com.example.message.config;

import com.example.message.model.User;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Глобальный совет контроллера для предоставления текущего пользователя всем шаблонам.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;

    @Autowired
    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    /**
     * Добавляет текущего пользователя в модель, чтобы он был доступен во всех шаблонах.
     *
     * @param model Модель для добавления атрибутов.
     */
    @ModelAttribute
    public void addCurrentUserToModel(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User currentUser = userService.findByUsername(username);
            model.addAttribute("currentUser", currentUser);
        }
    }
}
