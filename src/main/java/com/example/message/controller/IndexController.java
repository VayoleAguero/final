package com.example.message.controller;

import com.example.message.dto.EncryptionRequest;
import com.example.message.utils.cipher.ChaCha20;
import com.example.message.utils.cipher.RC4;
import com.example.message.utils.cipher.Salsa20;
import com.example.message.utils.cipher.XOR;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер для главной страницы с функционалом шифрования сообщений.
 * <p>
 * Этот контроллер обрабатывает запросы на отображение формы шифрования и обработку шифрования с использованием различных алгоритмов.
 * </p>
 */
@Controller
public class IndexController {

    /**
     * Обрабатывает GET-запрос для отображения главной страницы с формой для шифрования сообщений.
     *
     * @param model модель для передачи данных на представление.
     * @return имя шаблона страницы для отображения.
     */
    @GetMapping("/home")
    public String showEncryptionForm(Model model) {
        model.addAttribute("encryptionRequest", new EncryptionRequest());
        return "index";  // Возвращаем страницу для шифрования
    }

    /**
     * Обрабатывает POST-запрос с данными формы для шифрования сообщения.
     * После обработки шифрования, возвращает зашифрованное сообщение на главную страницу.
     *
     * @param encryptionRequest объект, содержащий данные для шифрования.
     * @param model модель для передачи зашифрованного сообщения на страницу.
     * @return имя шаблона страницы для отображения.
     */
    @PostMapping("/encrypt")
    public String encryptMessage(EncryptionRequest encryptionRequest, Model model) {
        // Получаем зашифрованное сообщение
        String encryptedMessage = handleEncryption(encryptionRequest);
        model.addAttribute("encryptedMessage", encryptedMessage);
        return "index";  // Возвращаем на главную страницу с результатом
    }

    /**
     * Логика шифрования, которая выполняет шифрование в зависимости от выбранного метода.
     *
     * @param encryptionRequest объект, содержащий данные для шифрования (сообщение, метод шифрования, ключ).
     * @return зашифрованное сообщение в виде строки.
     * @throws IllegalArgumentException если выбран неподдерживаемый метод шифрования.
     */
    private String handleEncryption(EncryptionRequest encryptionRequest) {
        String message = encryptionRequest.getMessage();  // Сообщение для шифрования
        String encryptionMethod = encryptionRequest.getEncryptionMethod();  // Метод шифрования
        String key = encryptionRequest.getKey();  // Ключ шифрования

        byte[] keyBytes = key.getBytes();  // Преобразуем ключ в байты

        switch (encryptionMethod.toUpperCase()) {
            case "RC4":
                // Шифруем с использованием RC4
                return new String(RC4.rc4EncryptDecrypt(message.getBytes(), keyBytes));
            case "CHACHA20":
                // Шифруем с использованием ChaCha20 (статичный nonce)
                byte[] nonce = new byte[8];  // Статичный nonce (можно передавать в запросе)
                return new String(ChaCha20.chacha20EncryptDecrypt(message.getBytes(), keyBytes, nonce));
            case "SALSA20":
                // Шифруем с использованием Salsa20 (статичный nonce)
                byte[] salsaNonce = new byte[8];  // Статичный nonce (можно передавать в запросе)
                return new String(Salsa20.salsa20Encrypt(message.getBytes(), keyBytes, salsaNonce));
            case "XOR":
                // Шифруем с использованием XOR
                return XOR.xorEncryptDecrypt(message, key);
            default:
                throw new IllegalArgumentException("Unsupported encryption method");  // Ошибка, если метод не поддерживается
        }
    }
}
