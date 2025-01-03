package com.example.message.utils.cipher;

/**
 * Класс для выполнения операции шифрования и дешифрования с использованием XOR.
 * <p>
 * Этот класс реализует метод для симметричного шифрования и дешифрования строк с использованием
 * побитовой операции XOR. Метод XOR можно использовать для шифрования и дешифрования текста,
 * так как операция XOR является обратимой: если применить XOR дважды с тем же ключом,
 * то оригинальное сообщение будет восстановлено.
 * </p>
 *
 * @see String#charAt(int)
 * @see StringBuilder#append(char)
 */
public class XOR {

    /**
     * Метод для шифрования или дешифрования строки с использованием операции XOR.
     * <p>
     * Этот метод применяет операцию XOR побитово между символами входной строки и ключа.
     * Длина ключа может быть меньше длины строки, в таком случае ключ будет повторяться
     * циклично для всей строки.
     * </p>
     *
     * @param input Входная строка для шифрования или дешифрования.
     * @param key Ключ для операции XOR. Если длина ключа меньше длины строки,
     *            то ключ будет повторяться.
     * @return Результат операции XOR с входной строкой, который является зашифрованным или
     *         расшифрованным текстом.
     */
    public static String xorEncryptDecrypt(String input, String key) {
        StringBuilder output = new StringBuilder();
        // Применяем операцию XOR побитово между символами входной строки и ключа
        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key.charAt(i % key.length())));
        }
        return output.toString();
    }
}
