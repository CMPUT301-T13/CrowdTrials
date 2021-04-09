package com.example.crowdtrials;

import org.junit.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactInfoTest {
    private ContactInfo mockContact = new ContactInfo("Bob", "7801234567");

    @Test
    void testContactInfoConstructor() {
        assertEquals("Bob", mockContact.getName());
        assertEquals("7801234567", mockContact.getPhoneNumber());

        mockContact.setName("Jack");
        mockContact.setPhoneNumber("7809876543");
        assertEquals("Jack", mockContact.getName());
        assertEquals("7809876543", mockContact.getPhoneNumber());
    }

}
