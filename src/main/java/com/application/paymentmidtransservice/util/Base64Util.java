package com.application.paymentmidtransservice.util;

import java.util.Base64;

public class Base64Util {

    private Base64Util() {}

    public static String encodeToBase64(String data) {
        byte[] bytesToEncode = data.getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(bytesToEncode);
        return new String(encodedBytes);
    }

    public static String base64Decode(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        return new String(decodedBytes);
    }

}
