package com.example.message.utils.cipher;

import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class Salsa20 {

    /**
     * Метод для шифрования данных с использованием алгоритма Salsa20.
     *
     * @param input Входные данные для шифрования.
     * @param key   Ключ длиной 32 байта.
     * @param nonce Вектор инициализации (IV) длиной 8 байт.
     * @return Зашифрованные данные.
     */
    public static byte[] salsa20Encrypt(byte[] input, byte[] key, byte[] nonce) {
        try {
            // Проверяем длину ключа и nonce
            if (key.length != 32) {
                throw new IllegalArgumentException("Ключ должен быть длиной 32 байта (256 бит)");
            }
            if (nonce.length != 8) {
                throw new IllegalArgumentException("Nonce должен быть длиной 8 байт");
            }

            Salsa20Engine engine = new Salsa20Engine();
            engine.init(true, new ParametersWithIV(new KeyParameter(key), nonce));

            byte[] output = new byte[input.length];
            engine.processBytes(input, 0, input.length, output, 0);
            return output;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка в Salsa20 шифровании", e);
        }
    }
}
