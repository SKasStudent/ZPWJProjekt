package org.example.security;

public class VigenereDecryption {
    public static String decrypt(String text, String key){

        StringBuilder res = new StringBuilder(text.length());
        String key_normalized = key.replaceAll("[^A-Za-z]","");

        int keyPosition = 0;

        for(char c:text.toCharArray()){
            if(c >= 'A' && c<= 'Z') {
                int shift = Character.toUpperCase(key_normalized.charAt(keyPosition % key_normalized.length())) - 'A';
                res.append((char) ('A' + Math.floorMod(c - 'A' + shift,26)));
                keyPosition++;
            }else if(c >= 'a' && c<= 'z'){
                int shift = Character.toUpperCase(key_normalized.charAt(keyPosition % key_normalized.length())) - 'A';
                res.append((char) ('a' + Math.floorMod(c - 'a' + shift,26)));
                keyPosition++;
            }else{
                res.append(c);
            }
        }
        return res.toString();
    }
}
