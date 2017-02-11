package com.github.barasher.esng.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.barasher.esng.data.Result;

public class ResultTest {

    @Test
    public void testMashalling() throws IOException {
	final ObjectMapper om = new ObjectMapper();
	final Result ref = new Result("u", true, 42);
	final String strVal = om.writeValueAsString(ref);
	final Result unmarshalled = om.readValue(strVal, Result.class);
	assertEquals(unmarshalled.getUnid(), ref.getUnid());
	assertEquals(unmarshalled.isAnwserCorrect(), ref.isAnwserCorrect());
	assertEquals(unmarshalled.getScore(), ref.getScore());
    }

}
