package com.pmk.twovidzoneclip.handler;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RestHandlerTest {

    private RestHandler handler;

    @Before
    public void setUp() {
        handler = new RestHandler();
    }

    @Test
    public void should_not_valid_string_with_chars_other_than_numbers() {
        //GIVEN
        final String testedStr = "12Bon34";

        //WHEN
        final boolean isNumber = handler.checkPageNumberFormat(testedStr);

        //THEN
        assertThat(isNumber).isFalse();
    }

    @Test
    public void should_not_valid_negative_number() {
        //GIVEN
        final String testedStr = "-12";

        //WHEN
        final boolean isNumber = handler.checkPageNumberFormat(testedStr);

        //THEN
        assertThat(isNumber).isFalse();
    }

    @Test
    public void should_valid_positive_numbers() {
        //GIVEN
        final String testedStr = "456";

        //WHEN
        final boolean isNumber = handler.checkPageNumberFormat(testedStr);

        //THEN
        assertThat(isNumber).isTrue();
    }
}
