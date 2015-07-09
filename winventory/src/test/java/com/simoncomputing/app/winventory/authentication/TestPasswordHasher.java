/**
 * @author brendan.goggin
 * 
 * PasswordHasher is a class of static functions used for
 * generating and checking hashed passwords
 * 
 */

package com.simoncomputing.app.winventory.authentication;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.simoncomputing.app.winventory.authentication.*;

public class TestPasswordHasher {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    // Methods to run before and after each test

    // @Before
    // public void setup() {
    //
    // }
    //
    // @After
    // public void takeDown() {
    //
    // }

    
    
    /**
     * Test PasswordHasher.encodePassword() and checkPassword() with random
     * valid passwords
     */
    @Test
    public void testEncodePassword() {

        Random rand = new Random();

        for (int i = 0; i < 200; i++) {

            // generate random plaintext with random length between 8 and 32 chars
            String plaintext = "";
            int textLength = rand.nextInt(25) + 8;
            for (int j = 0; j < textLength; j++) {
                plaintext += (char) rand.nextInt(256);
            }

            // hash the plaintext
            String hashedPassword = PasswordHasher.encodePassword(plaintext);

            assertTrue(PasswordHasher.checkPassword(plaintext, hashedPassword));

        }
    }

    
    
    /**
     * Test PasswordHasher.encodePassword() with invalid passwords
     * expects IllegalArgumentExceptions to be thrown with each password
     */
    @Test
    public void testEncodePasswordFails() {
        
        Random rand = new Random();
        
        // test passwords that are too short
        for (int i = 0; i < 500; i++) {

            // generate random plaintext with random length less than 8 chars
            String plaintext = "";
            int textLength = rand.nextInt(8);
            for (int j = 0; j < textLength; j++) {
                plaintext += (char) rand.nextInt(256);
            }

            // assert that IllegalArgumentException is thrown
            exception.expect(IllegalArgumentException.class);
            PasswordHasher.encodePassword(plaintext);
            exception = ExpectedException.none();
            
        }
        
        // test passwords that are too long
        for (int i = 0; i < 500; i++) {

            // generate random plaintext with random length greater than 32 chars
            String plaintext = "";
            int textLength = rand.nextInt(32) + 33;
            for (int j = 0; j < textLength; j++) {
                plaintext += (char) rand.nextInt(256);
            }

            // assert that IllegalArgumentException is thrown
            exception.expect(IllegalArgumentException.class);
            PasswordHasher.encodePassword(plaintext);
            exception = ExpectedException.none();
            
        }
    }

}