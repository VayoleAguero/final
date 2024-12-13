package com.example.message.utils.cipher;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * Класс для шифрования и дешифрования данных с использованием алгоритма ChaCha20.
 * <p>
 * Этот класс реализует шифрование и дешифрование данных с использованием алгоритма
 * ChaCha20, который является улучшенной версией алгоритма ChaCha и используется для
 * обеспечения высокой безопасности в потоковых шифрах.
 * </p>
 *
 * @see ChaChaEngine
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
     * @param key Ключ для шифрования. Должен быть известен как для шифрования, так и для дешифрования.
     * @param nonce Число одноразового использования (nonce), которое помогает избежать повторения ключей.
     * @return Результат шифрования или дешифрования в виде массива байтов.
     */
    public static byte[] chacha20EncryptDecrypt(byte[] input, byte[] key, byte[] nonce) {
        // Инициализация движка ChaCha20 с ключом и nonce
        ChaChaEngine engine = new ChaChaEngine();
        engine.init(true, new KeyParameter(key));

        // Массив для хранения выходных данных
        byte[] output = new byte[input.length];

        // Применяем процесс шифрования или дешифрования
        engine.processBytes(input, 0, input.length, output, 0);

        return output;
    }
}
