package com.example.dbsservice.utils;

import java.util.Random;

public class Generator {
    public static String generateCif() {
        // Độ dài của chuỗi ngẫu nhiên
        int length = 8;

        // Khai báo các chữ số từ 0 đến 9
        String digits = "0123456789";

        // Sử dụng StringBuilder để xây dựng chuỗi ngẫu nhiên
        StringBuilder randomString = new StringBuilder();

        // Tạo một đối tượng Random
        Random random = new Random();

        // Tạo chuỗi ngẫu nhiên bằng cách chọn ngẫu nhiên các chữ số từ 'digits'
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(digits.length());
            char randomDigit = digits.charAt(randomIndex);
            randomString.append(randomDigit);
        }

        // Trả về chuỗi ngẫu nhiên
        return randomString.toString();
    }

    public static String generateAccountNumber(String cif, boolean existAccount, String accountNumber) {
        String accountNew = null;
        if (existAccount) {
            StringBuilder accountNumberNew = new StringBuilder(String.valueOf(Integer.parseInt(accountNumber) + 1));
            do {
                accountNumberNew.insert(0, "0");
            } while (accountNumberNew.length() < 11);
            accountNew = accountNumberNew.toString();
        } else {
            accountNew =  cif + "0";
        }
        return accountNew;
    }
}
