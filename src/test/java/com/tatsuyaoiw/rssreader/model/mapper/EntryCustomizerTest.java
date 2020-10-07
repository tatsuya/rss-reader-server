package com.tatsuyaoiw.rssreader.model.mapper;

import com.tatsuyaoiw.rssreader.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EntryCustomizerTest {

    private EntryCustomizer customizer;

    @BeforeEach
    void beforeEach() {
        customizer = new EntryCustomizer(20, 140);
    }

    @Test
    void test_titleIsNull() {
        Entry input = Entry.builder()
                           .title(null)
                           .description("Description")
                           .build();

        Entry actual = customizer.apply(input);
        assertNull(actual.getTitle());
        assertEquals("Description", actual.getDescription());
    }

    @Test
    void test_descriptionIsNull() {
        Entry input = Entry.builder()
                           .title("Title")
                           .description(null)
                           .build();

        Entry actual = customizer.apply(input);
        assertEquals("Title", actual.getTitle());
        assertNull(actual.getDescription());
    }

    @Test
    void test_long() {
        Entry input = Entry.builder()
                           .title("At Least 235 Killed in Egypt Mosque Attack")
                           .description("At least 235 people were killed and 109 injured when gunmen armed with explosives attacked a Sufi-linked mosque in Egypt’s restive Sinai Peninsula, in the deadliest assault in the country’s modern history.")
                           .build();

        Entry actual = customizer.apply(input);
        assertEquals(20, actual.getTitle().length());
        assertEquals(140, actual.getDescription().length());
    }

    @Test
    void test_short() {
        String shortTitle = "Short Title";
        String shortDescription = "Short description";
        Entry input = Entry.builder()
                           .title(shortTitle)
                           .description(shortDescription)
                           .build();
        Entry actual = customizer.apply(input);
        assertEquals(shortTitle.length(), actual.getTitle().length());
        assertEquals(shortDescription.length(), actual.getDescription().length());
    }
}