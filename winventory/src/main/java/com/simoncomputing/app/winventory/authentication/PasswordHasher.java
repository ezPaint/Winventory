/**
 * @author brendan.goggin
 * 
 * PasswordHasher is a class of static functions used for
 * generating and checking hashed passwords
 * 
 */

package com.simoncomputing.app.winventory.authentication;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordHasher {

    private static SecureRandom random = new SecureRandom();
    
    
    
    /**
     * Checks if the plaintext hashes to the given password
     * 
     * @param plaintext the plaintext to be checked
     * @param password the hashed password against which to check
     * @return true if the plaintext hashes to the password
     */
    public static boolean checkPassword(String plaintext, String password) {
        
        // ensure params are not null
        if (plaintext == null || password == null) {
            return false;
        }

        // extract the hash parameters from the encoded password
        String[] hashInfo = password.split("\\$");
        String algorithm = hashInfo[0];
        int iterations = Integer.parseInt(hashInfo[1]);
        String encoding = hashInfo[2];
        String salt = hashInfo[3];

        // compare the password to the hashed plaintext, using the proper hash parameters
        return password.equals(computeHash(algorithm, iterations, encoding, salt, plaintext));
    }

    
    
    /**
     * returns the hashed and salted password from the plaintext ready to be
     * stored in the database
     * 
     * @param plaintext must be between 8 and 32 characters long
     * @return password
     * @throws IllegalArgumentException if the plaintext 
     *              is not between 8 and 32 characters long (inclusive)
     */
    public static String encodePassword(String plaintext) {
        if (plaintext.length() < 8) {
            throw new IllegalArgumentException("Password must contain "
                    + "at least 8 characters.");
        }
        if (plaintext.length() > 32) {
            throw new IllegalArgumentException("Password must contain "
                    + "no more than 32 characters");
        }
        // hashing parameters
        String algorithm = "SHA-256";
        String encoding = "UTF-8";
        int iterations = 20000;
        String salt = makeSalt();

        // perform the hash
        String password = computeHash(algorithm, iterations, encoding, salt, plaintext);
        return password;
    }

    
    
    /**
     * Generates hashed/encoded text from the given plaintext and parameters
     * 
     * @param algorithm
     * @param iterations
     * @param encoding
     * @param salt
     * @param plaintext
     * @return
     */
    private static String computeHash(String algorithm, int iterations, String encoding, String salt,
            String plaintext) {

        try {

            // specify hash algorithm
            MessageDigest sha = MessageDigest.getInstance(algorithm);

            // prepend the salt to the plaintext and save the byte encoding
            byte[] hashedText = (salt + plaintext).getBytes(encoding);

            // iterate the hash as many times as specified
            for (int i = 0; i < iterations; i++) {
                hashedText = sha.digest(hashedText);
            }

            // compose the password in form
            // "$algorithm$iterations$encoding$salt$hashedText$"
            String password = sha.getAlgorithm() + "$";
            password += iterations + "$";
            password += encoding + "$";
            password += salt + "$";
            password += hexEncode(hashedText);

            // return the resulting password, ready to be saved in the DB
            return password;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm as:  " + algorithm);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding: " + encoding);
        }
        return null;
    }

    
    
    /**
     * encodes the byte array as a string of hex characters does not begin
     * string with "0x"
     * 
     * @param input
     * @return
     */
    private static String hexEncode(byte[] input) {

        StringBuffer hex = new StringBuffer();

        // the 16 possible hex digits
        char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };

        // convert the bytes to digits and append to hex
        for (int i = 0; i < input.length; i++) {
            byte b = input[i];
            
            // effectively ANDs each byte with '1111' and appends to hex
            hex.append( digits[(b & 0xf0) >> 4] );
            hex.append( digits[ b & 0x0f ] );
        }

        return hex.toString();
    }

    
    
    /**
     * Generates a random salt, 32 char long
     * 
     * @return salt string of length 32 containing the random salt
     */
    private static String makeSalt() {
        return new BigInteger(130, random).toString(32);
    }

}