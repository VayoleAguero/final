package com.example.message.controller;

import com.example.message.dto.EncryptionRequest;
import com.example.message.utils.cipher.ChaCha20;
import com.example.message.utils.cipher.Salsa20;
import com.example.message.utils.cipher.RC4;
import com.example.message.utils.cipher.XOR;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Arrays;

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
     * Поддерживаемые алгоритмы шифрования: ChaCha20, Salsa20, RC4, XOR.
     * </p>
     *
     * @param request запрос, содержащий сообщение, метод шифрования и ключ.
     * @return зашифрованное сообщение в формате Base64.
     * @throws IllegalArgumentException если метод шифрования не поддерживается.
     */
    @PostMapping
    public String encryptMessage(@RequestBody EncryptionRequest request) {
        try {
            String message = request.getMessage();
            String encryptionMethod = request.getEncryptionMethod();
            String key = request.getKey();

            byte[] encryptedBytes;
            byte[] keyBytes;
            byte[] nonce;

            switch (encryptionMethod.toUpperCase()) {
                case "CHACHA20":
                    keyBytes = generateKey(32);
                    nonce = generateNonce(8);
                    encryptedBytes = ChaCha20.chacha20EncryptDecrypt(message.getBytes(), keyBytes, nonce);
                    break;
                case "SALSA20":
                    keyBytes = generateKey(32);
                    nonce = generateNonce(8);
                    encryptedBytes = Salsa20.salsa20Encrypt(message.getBytes(), keyBytes, nonce);
                    break;
                case "RC4":
                    keyBytes = key.getBytes();
                    encryptedBytes = RC4.rc4EncryptDecrypt(message.getBytes(), keyBytes);
                    break;
                case "XOR":
                    String encryptedXor = XOR.xorEncryptDecrypt(message, key);
                    return Base64.getEncoder().encodeToString(encryptedXor.getBytes());
                default:
                    throw new IllegalArgumentException("Unsupported encryption method: " + encryptionMethod);
            }

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка шифрования: " + e.getMessage(), e);
        }
    }

    /**
     * Генерация случайного ключа заданной длины.
     *
     * @param length длина ключа в байтах.
     * @return сгенерированный ключ в виде массива байтов.
     */
    private byte[] generateKey(int length) {
        byte[] key = new byte[length];
        new SecureRandom().nextBytes(key);
        return key;
    }

    /**
     * Генерация случайного nonce заданной длины.
     *
     * @param length длина nonce в байтах.
     * @return сгенерированный nonce.
     */
    private byte[] generateNonce(int length) {
        byte[] nonce = new byte[length];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}
