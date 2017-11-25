package com.tatsuyaoiw.model.mapper;

import com.tatsuyaoiw.model.Entry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntryCustomizerTest {

    private EntryCustomizer customizer;

    @Before
    public void before() throws Exception {
        customizer = new EntryCustomizer(20, 140);
    }

    @Test
    public void test_long() throws Exception {
        Entry input = Entry.builder()
                           .title("At Least 235 Killed in Egypt Mosque Attack")
                           .description("At least 235 people were killed and 109 injured when gunmen armed with explosives attacked a Sufi-linked mosque in Egypt’s restive Sinai Peninsula, in the deadliest assault in the country’s modern history.")
                           .build();

        Entry actual = customizer.apply(input);
        assertEquals(20, actual.getTitle().length());
        assertEquals(140, actual.getDescription().length());
    }

    @Test
    public void test_short() throws Exception {
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