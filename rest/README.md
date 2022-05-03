# Simple REST test
The original code is based on a [Spring Boot REST example](https://github.com/gabrielcostasilva/sb-controllers/tree/main/rest). The REST controller (`com.example.rest.RESTController`) has two methods: `GET` and `POST`. Whereas the `GET` method returns a _Hello World_ text, the `POST` method returns the _Hello World_ followed by the path variable value. 

## Project Overview
The `RESTControllerTest` class, in the `test` folder is responsible for running two tests that check the two `RESTController` methods.

The code below fully shows the `RESTControllerTest` content.

```java
@WebMvcTest // (1)
public class RESTControllerTest {

	@Autowired
	private MockMvc mockMvc; // (2)

	@Test // (3)
	public void getHello() throws Exception {
		this.mockMvc
				.perform(get("/")) // (4)
				.andExpect(status().isOk()) // (5)
				.andExpect(content().string("Hello world?"));
	}

	@Test // (6)
	public void getCustomisedHello() throws Exception {
		this.mockMvc
				.perform(post("/{name}", "John")) // (7)
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World, John")); // (8)
	}

}
```
1. Loads the necessary context for running MVC tests.
2. Injects a `MockMvc` object that enables the test to communicate directly with the Spring Boot controller.
3. Defines a test.
4. Uses the `MockMvc` object for performing a `org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get`, representing a `GET` request to "`/`". 
5. Checks whether the return is a `200`.
6. Checks whether the response content is a _Hello World?_.

The second test is similar (6). The difference is that the method is a `POST`, which send a parameter (`name`) with the value `John` (7). As a result, the expected return is the original text followed by `John`.