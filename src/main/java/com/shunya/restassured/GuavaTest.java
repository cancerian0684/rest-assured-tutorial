package com.shunya.restassured;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class GuavaTest {

    public void testHashingSha256() {
        final HashFunction hashFunction = Hashing.sha256();
        final HashCode hc = hashFunction
                .newHasher()
                .putString("The quick brown fox jumps over the lazy dog", Charsets.UTF_8)
                .hash();
        final String sha256 = hc.toString();
        System.out.println("sha256 = " + sha256);
    }

    public static void main(String[] args) {
        new GuavaTest().testHashingSha256();
    }
}
