package com.shunya.restassured;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class OtpGenerator {

    private final SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

    public OtpGenerator() throws NoSuchAlgorithmException {
    }

    public String generate(int maxLength) {
        final StringBuilder otp = new StringBuilder(maxLength);
        for (int i = 0; i < maxLength; i++) {
            otp.append(secureRandom.nextInt(9));
        }
        return otp.toString();
    }

    public String generate(int maxLength, String allowedChars) {
        final StringBuilder otp = new StringBuilder(maxLength);
        for (int i = 0; i < maxLength; i++) {
            otp.append(allowedChars.charAt(secureRandom.nextInt(allowedChars.length())));
        }
        return otp.toString();
    }

    public String generateV2(int maxLength, String allowedChars) {
        final char[] characters = allowedChars.toCharArray();
        byte[] randomBytes = new byte[maxLength];
        secureRandom.nextBytes(randomBytes);
        char[] chars = new char[randomBytes.length];
        for (int i = 0; i < randomBytes.length; i++) {
            chars[i] = characters[((randomBytes[i] & 0xFF) % characters.length)];
        }
        return new String(chars);
    }

    public String generateInsecure(int maxLength, String allowedChars) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        final StringBuilder otp = new StringBuilder(maxLength);
        for (int i = 0; i < maxLength; i++) {
            otp.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return otp.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        OtpGenerator otpGenerator = new OtpGenerator();
        String otpInsecure = otpGenerator.generateInsecure(8, "123456789");
        System.out.println(otpInsecure);

        String otp1 = otpGenerator.generate(8);
        System.out.println(otp1);

        String otp2 = otpGenerator.generate(8, "123456789ABCDE");
        System.out.println(otp2);

        String otp3 = otpGenerator.generateV2(8, "123456789ABCDE");
        System.out.println(otp3);
    }

}
