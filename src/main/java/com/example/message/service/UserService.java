package com.example.message.service;

import com.example.message.model.User;
import com.example.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления пользователями.
 * <p>
 * Этот сервис предоставляет методы для работы с сущностью {@link User}. Он включает операции
 * для добавления нового пользователя, обновления информации о пользователе, поиска пользователя
 * по имени и ID, а также проверки введенных данных для входа.
 * </p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для инъекции зависимостей {@link UserRepository} и {@link PasswordEncoder}.
     *
     * @param userRepository Репозиторий для работы с пользователями.
     * @param passwordEncoder Кодировщик паролей.
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Сохраняет нового пользователя с зашифрованным паролем.
     *
     * @param user Объект пользователя, который будет сохранен.
     */
    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль перед сохранением
        userRepository.save(user);
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param user Обновленный объект пользователя.
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем или null, если не найден.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Проверяет введенные данные для входа.
     * <p>
     * Сравнивает введенный пароль с зашифрованным паролем, хранящимся в базе данных.
     * </p>
     *
     * @param username Имя пользователя.
     * @param rawPassword Введенный пароль.
     * @return true, если введенный пароль совпадает с хранимым, иначе false.
     */
    public boolean checkCredentials(String username, String rawPassword) {
        User user = findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(rawPassword, user.getPassword()); // Проверка пароля
        }
        return false;
    }

    /**
     * Получает всех пользователей.
     *
     * @return Список всех пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с заданным идентификатором или null, если не найден.
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Дополнительный метод для получения пользователя по его ID.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с заданным идентификатором или null, если не найден.
     */
    public User getUserById(Long id) {
        return findById(id);  // Этот метод просто вызывает существующий findById
    }
}
