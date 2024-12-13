package com.example.message.controller;

import com.example.message.dto.EncryptionRequest;
import com.example.message.utils.cipher.ChaCha20;
import com.example.message.utils.cipher.RC4;
import com.example.message.utils.cipher.Salsa20;
import com.example.message.utils.cipher.XOR;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для шифрования сообщений.
 * <p>
 * Этот контроллер предоставляет REST API для шифрования сообщений с использованием различных алгоритмов.
 * </p>
 */
@RestController
@RequestMapping("/api/encrypt")
public class EncryptionController {

    /**
     * Шифрует сообщение с использованием указанного метода шифрования.
     * <p>
     * Поддерживаемые алгоритмы шифрования: RC4, ChaCha20, Salsa20, XOR.
     * </p>
     *
     * @param request запрос, содержащий сообщение, метод шифрования и ключ.
     * @return зашифрованное сообщение в виде строки.
     * @throws IllegalArgumentException если метод шифрования не поддерживается.
     */
    @PostMapping
    public String encryptMessage(@RequestBody EncryptionRequest request) {
        String message = request.getMessage(); // Сообщение для шифрования
        String encryptionMethod = request.getEncryptionMethod(); // Метод шифрования
        String key = request.getKey(); // Ключ шифрования

        byte[] keyBytes = key.getBytes(); // Преобразуем ключ в байты

        switch (encryptionMethod.toUpperCase()) {
            case "RC4":
                // Используем RC4 для шифрования/расшифрования
                return new String(RC4.rc4EncryptDecrypt(message.getBytes(), keyBytes));
            case "CHACHA20":
                // Используем ChaCha20 для шифрования/расшифрования (статичный nonce)
                byte[] nonce = new byte[8]; // Статичный nonce (можно передавать в запросе)
                return new String(ChaCha20.chacha20EncryptDecrypt(message.getBytes(), keyBytes, nonce));
            case "SALSA20":
                // Используем Salsa20 для шифрования (статичный nonce)
                byte[] salsaNonce = new byte[8]; // Статичный nonce (можно передавать в запросе)
                return new String(Salsa20.salsa20Encrypt(message.getBytes(), keyBytes, salsaNonce));
            case "XOR":
                // Используем XOR для шифрования/расшифрования
                return XOR.xorEncryptDecrypt(message, key);
            default:
                throw new IllegalArgumentException("Unsupported encryption method"); // Ошибка, если метод не поддерживается
        }
    }
}
