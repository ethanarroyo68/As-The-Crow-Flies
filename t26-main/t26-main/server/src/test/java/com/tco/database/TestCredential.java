package com.tco.database;

import com.tco.misc.Place;
import com.tco.misc.Places;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCredential {

    @Test
    @DisplayName("mboin: Test Constructor")
    void testConstructor() {
        Credential credential = new Credential();
        assertNotNull(credential, "Should be able to create a Credential instance");
    }

    @Test
    @DisplayName("mboin: Credential constants should not be null or empty")
    void testConstantsNotNullOrEmpty() {
        assertNotNull(Credential.USER, "USER should not be null");
        assertFalse(Credential.USER.isEmpty(), "USER should not be empty");

        assertNotNull(Credential.PASSWORD, "PASSWORD should not be null");
        assertFalse(Credential.PASSWORD.isEmpty(), "PASSWORD should not be empty");

        assertNotNull(Credential.URL, "URL should not be null");
        assertFalse(Credential.URL.isEmpty(), "URL should not be empty");
    }
    
    @Test
    @DisplayName("dampierj: Test User")
    void testUserConstant() {
        assertEquals("cs314-db", Credential.USER, "USER constant should match the expected value.");
    }

    @Test
    @DisplayName("dampierj: Test Password")
    void testPasswordConstant() {
        assertEquals("eiK5liet1uej", Credential.PASSWORD, "PASSWORD constant should match the expected value.");
    }

    @Test
    @DisplayName("dampierj: Test Url")
    void testUrlConstant() {
        assertEquals("jdbc:mariadb://faure.cs.colostate.edu/cs314", Credential.URL, "URL constant should match the expected value.");
    }

    @Test
    @DisplayName("dampierj: Test Url Method")
    void testUrlMethod() {
        assertEquals(Credential.URL, Credential.url(), "url() method should return the same value as the URL constant.");
    }
}
