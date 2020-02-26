package com.blo.reade;

import org.junit.Before;
import org.junit.Test;

import controller.TimeAgoConverter;

import static org.junit.Assert.*;

public class TimeAgoConverterTest {
    String input;
    String output;
    String expected;
    String defaultDate;

    @Before
    public void setUp() {
        input = "Fri, 31 Jan 2020 23:15:30 GMT";
        defaultDate = "Unavailable date";
    }


    @Test
    public void testforNull() {
        output = TimeAgoConverter.convertToTimeAgo(null);
        assertEquals(output, defaultDate);
    }

    @Test
    public void testforEmpty() {
        output = TimeAgoConverter.convertToTimeAgo("");
        assertEquals(output, defaultDate);
    }


    @Test
    public void testforNoTimezones() {
        output = TimeAgoConverter.convertToTimeAgo("Friday, 31 Jan 2020 23:30:30");
        assertEquals(output, defaultDate);
    }

    @Test
    public void testforIncompTimezones() {
        output = TimeAgoConverter.convertToTimeAgo("Sat, 01 Feb 2020 23:50:30 +050");
        assertEquals(output, defaultDate);
    }

    @Test
    public void testforNoDayAndTimezones() {
        output = TimeAgoConverter.convertToTimeAgo("01 Feb 2020 23:50:30");
        assertEquals(output, defaultDate);
    }

    @Test
    public void testforNoDayAndTime() {
        output = TimeAgoConverter.convertToTimeAgo("01 Feb 2020");
        assertEquals(output, defaultDate);
    }


    @Test
    public void testforUnacceptedInput() {
        output = TimeAgoConverter.convertToTimeAgo("Sat, 01 Feb 2020 !&23:50:30 GMT");
        assertEquals(output, defaultDate);
    }

}