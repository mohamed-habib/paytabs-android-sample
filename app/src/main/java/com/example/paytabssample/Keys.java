package com.example.paytabssample;

public class Keys {
    private static native String getSecretKey();

    private static native String getMerchantEmail();

    public static String readSecretKey() {//use this method for String
        return getSecretKey();
    }

    public static String readMerchantEmail() {//use this method for String
        return getMerchantEmail();
    }

    static {
        System.loadLibrary("keys");
    }
}
