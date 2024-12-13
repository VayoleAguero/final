package com.example.message.utils.cipher;

import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * Класс для шифрования с использованием алгоритма Salsa20.
 * <p>
 * Этот класс реализует шифрование данных с использованием поточного шифра Salsa20.
 * Salsa20 является высокоскоростным симметричным шифром, который применяет операцию
 * потокового шифрования на основе заданного ключа и инициализационного вектора (IV).
 * </p>
 *
 * @see Salsa20Engine
 */
public class Salsa20 {

    /**
     * Метод для шифрования данных с использованием алгоритма Salsa20.
     * <p>
     * Этот метод использует потоковый шифр Salsa20 для шифрования входных данных.
     * Потоковый шифр применяет побитовую операцию XOR с использованием сгенерированного
     * ключа и инициализационного вектора для каждого блока данных.
     * </p>
     *
     * @param input Входной массив байтов для шифрования.
     * @param key Ключ для шифрования. Должен быть 256-битным (32 байта).
     * @param nonce Инициализационный вектор для алгоритма Salsa20.
     * @return Зашифрованный массив байтов.
     */
    public static byte[] salsa20Encrypt(byte[] input, byte[] key, byte[] nonce) {
        // Инициализируем движок Salsa20 с ключом
        Salsa20Engine engine = new Salsa20Engine();
        engine.init(true, new KeyParameter(key));

        // Массив для хранения зашифрованного вывода
        byte[] output = new byte[input.length];

        // Применяем процесс шифрования
        engine.processBytes(input, 0, input.length, output, 0);

        return output;
    }
}
