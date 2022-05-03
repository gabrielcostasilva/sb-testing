package com.example.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class RESTControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getHello() throws Exception {
		this.mockMvc
				.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello world?"));
	}

	@Test
	public void getCustomisedHello() throws Exception {
		this.mockMvc
				.perform(post("/{name}", "John"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World, John"));
	}

}
