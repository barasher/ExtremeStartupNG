package com.github.barasher.esng.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.barasher.esng.data.Question;

public class QuestionTest {

    @Test
    public void testMashalling() throws IOException {
	final ObjectMapper om = new ObjectMapper();
	final Question ref = new Question("u", "q");
	final String strVal = om.writeValueAsString(ref);
	final Question unmarshalled = om.readValue(strVal, Question.class);
	assertEquals(ref, unmarshalled);
    }

}
