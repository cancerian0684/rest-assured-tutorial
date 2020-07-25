package com.shunya.restassured;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HMacTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String valueToDigest = "The quick brown fox jumps over the lazy dog";
        byte[] key = "secret".getBytes();

        HMacTest test = new HMacTest();
        String messageDigest = test.generateHmac256(valueToDigest, key);
        System.out.println(messageDigest);

        HmacUtils hm256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key);
        String hmac = hm256.hmacHex(valueToDigest);
        System.out.println(hmac);

        String gHmac = Hashing.hmacSha256(key)
                .newHasher()
                .putString(valueToDigest, UTF_8)
                .hash()
                .toString();
        System.out.println(gHmac);
    }

    String generateHmac256(String message, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] bytes = hmac("HmacSHA256", key, message.getBytes());
        BaseEncoding.base16().lowerCase().encode(bytes);
        return bytesToHex(bytes);
    }

    byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
