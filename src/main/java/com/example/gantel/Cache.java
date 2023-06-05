package com.example.gantel;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Dictionary;

public class Cache {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String FILE_PATH = "tokens.txt";

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private static ArrayList<String> tokens = new ArrayList<String>();

    static {
        tokens = loadTokens();
    }

    public static String addToken(String email) {
        String token = generateRandomString(9);
        tokens.add(token);
        saveTokens();
        return token;
    }

    public static boolean exists(String token) {
        return tokens.contains(token);
    }

    private static ArrayList<String> loadTokens() {
        ArrayList<String> loadedTokens = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedTokens.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to load tokens: " + e.getMessage());
        }
        return loadedTokens;
    }

    private static void saveTokens() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String token : tokens) {
                writer.write(token);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save tokens: " + e.getMessage());
        }
    }
}
