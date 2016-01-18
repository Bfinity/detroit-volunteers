package com.detroitlabs.detroitvolunteers.client;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class WSSEGenerator {

    public static String createWSSE() {
        String accountName = "andrewjb";
        String apiKey = "80f51f19cce8f62e4e8831e0e4539772";
        Random random = new Random();
        String nonce = String.valueOf(random.nextInt(999999));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        String timestamp = dateFormat.format(new Date());

        String digestInput = nonce + timestamp + apiKey;
        String passwordDigest = "";
        try {
            passwordDigest = Base64.encodeToString(sha256((digestInput).getBytes("UTF-8")), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            Log.i("encodingException", e.getLocalizedMessage());
        }

        StringBuilder credentials = new StringBuilder();
        credentials.append("UsernameToken Username=\"").append(accountName).append("\", ");
        credentials.append("PasswordDigest=\"").append(passwordDigest.trim()).append("\", ");
        credentials.append("Nonce=\"").append(nonce.trim()).append("\", ");
        credentials.append("Created=\"").append(timestamp).append("\"");

        Log.i("creds", credentials.toString());
        return credentials.toString();
    }

    /**
     * Generates a SHA-256 hash of a payload message.
     *
     * @param payload
     * @return
     */
    private static byte[] sha256(byte[] payload) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            return digest.digest(payload);
        } catch (NoSuchAlgorithmException e) {
            Log.e("error", "error");
        }
        return null;
    }
}
