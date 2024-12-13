package com.example.message.dto;

/**
 * DTO (Data Transfer Object) для запроса на шифрование.
 * <p>
 * Этот класс используется для передачи данных, необходимых для выполнения операции шифрования сообщения.
 * </p>
 */
public class EncryptionRequest {

    private String message;
    private String encryptionMethod;
    private String key;

    /**
     * Получить сообщение, которое требуется зашифровать.
     *
     * @return Сообщение, которое будет зашифровано.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Установить сообщение, которое требуется зашифровать.
     *
     * @param message Сообщение, которое необходимо зашифровать.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Получить метод шифрования, который должен быть использован.
     *
     * @return Метод шифрования (например, "XOR", "Salsa20").
     */
    public String getEncryptionMethod() {
        return encryptionMethod;
    }

    /**
     * Установить метод шифрования.
     *
     * @param encryptionMethod Метод шифрования (например, "XOR", "Salsa20").
     */
    public void setEncryptionMethod(String encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    /**
     * Получить ключ для шифрования.
     *
     * @return Ключ для шифрования.
     */
    public String getKey() {
        return key;
    }

    /**
     * Установить ключ для шифрования.
     *
     * @param key Ключ для шифрования.
     */
    public void setKey(String key) {
        this.key = key;
    }
}
