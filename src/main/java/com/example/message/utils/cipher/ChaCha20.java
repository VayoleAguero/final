package com.example.message.utils.cipher;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * Класс для шифрования и дешифрования данных с использованием алгоритма ChaCha20.
 * <p>
 * Этот класс реализует шифрование и дешифрование данных с использованием алгоритма
 * ChaCha20, который является современным потоковым шифром.
 * </p>
 */
public class ChaCha20 {

    /**
     * Метод для шифрования или дешифрования данных с использованием алгоритма ChaCha20.
     * <p>
     * Потоковый шифр ChaCha20 генерирует псевдослучайный поток данных с использованием ключа
     * и nonce, который затем применяется к данным с помощью операции XOR для шифрования и дешифрования.
     * </p>
     *
     * @param input Входной массив байтов для шифрования или дешифрования.
     * @param key   Ключ для шифрования. Должен быть 256-битным (32 байта).
     * @param nonce Число одноразового использования (nonce), длина должна быть 8 байт.
     * @return Результат шифрования или дешифрования в виде массива байтов.
     */
    public static byte[] chacha20EncryptDecrypt(byte[] input, byte[] key, byte[] nonce) {
        try {
            // Проверяем длину ключа и nonce
            if (key.length != 32) {
                throw new IllegalArgumentException("Ключ должен быть длиной 32 байта (256 бит)");
            }
            if (nonce.length != 8) {
                throw new IllegalArgumentException("Nonce должен быть длиной 8 байт");
            }

            ChaChaEngine engine = new ChaChaEngine();
            ParametersWithIV params = new ParametersWithIV(new KeyParameter(key), nonce);
            engine.init(true, params); // true - для шифрования

            byte[] output = new byte[input.length];
            engine.processBytes(input, 0, input.length, output, 0);
            return output;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка в ChaCha20 шифровании", e);
        }
    }
}
