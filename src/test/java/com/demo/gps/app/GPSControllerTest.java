package com.demo.gps.app;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GPSControllerTest {

	@SuppressWarnings("unused")
	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}

	@Test
	public void testUploadMaps() throws Exception {

		File file = new File("D:\\sample.gpx");
		InputStream in = new FileInputStream(file);
		byte fileContent[] = new byte[(int) file.length()];
		in.read(fileContent);
		MockMultipartFile uploadedFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileContent);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/gps/upload").file(uploadedFile).param("userName", "Sam"))
				.andExpect(status().is(200)).andExpect(content().string("success"));
	}

	@Test
	public void testUploadMaps2() throws Exception {

		File file = new File("D:\\sample.gpx");
		InputStream in = new FileInputStream(file);
		byte fileContent[] = new byte[(int) file.length()];
		in.read(fileContent);
		MockMultipartFile uploadedFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileContent);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/gps/upload").file(uploadedFile).param("userName", "Sam"))
				.andExpect(status().is(200)).andExpect(content().string("success"));
	}

	@Test
	public void testGetUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users/find/{name}", "Sam")).andExpect(status().is(200))
				.andExpect(content().string("Sam"));
	}

	@Test
	public void testGetLatestPosition() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users/latestposition/{name}", "Sam")).andExpect(status().is(200))
				.andExpect(content().string("42.22087836969921 : -1.4580971002578733"));
	}

	@Test
	public void getUserMaps() throws Exception {
		String username = "Sam";
		ResponseEntity<String> response = template.getForEntity(base.toString() + "gps/loadUserMap/" + username,
				String.class);
		Assert.assertNotNull(response.getBody());
		Assert.assertNotEquals("[]", response.getBody());
	}
}
