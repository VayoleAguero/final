package com.example.message.dto;

/**
 * DTO (Data Transfer Object) для запроса на шифрование.
 * <p>
 * Этот класс используется для передачи данных, необходимых для выполнения операции шифрования сообщения.
 * </p>
 */
public class EncryptionRequest {

    /**
     * Сообщение, которое требуется зашифровать.
     */
    private String message;

    /**
     * Метод шифрования, который должен быть использован.
     */
    private String encryptionMethod;

    /**
     * Ключ для шифрования.
     */
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
     * @return Метод шифрования (например, "RC4", "CHACHA20").
     */
    public String getEncryptionMethod() {
        return encryptionMethod;
    }

    /**
     * Установить метод шифрования.
     *
     * @param encryptionMethod Метод шифрования (например, "RC4", "CHACHA20").
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

    @Override
    public String toString() {
        return "EncryptionRequest{" +
                "message='" + message + '\'' +
                ", encryptionMethod='" + encryptionMethod + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
