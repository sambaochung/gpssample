package com.demo.gps.app;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GPSControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Test
    public void testUploadMaps() throws Exception {
    	
    	  File file = new File("D:\\sample.gpx");
    	  InputStream in = new FileInputStream(file);
    	  byte fileContent[] = new byte[(int)file.length()];
    	  in.read(fileContent);
    	  MockMultipartFile uploadedFile = new MockMultipartFile("file", "filename.txt", "text/plain",fileContent);
          MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
          mockMvc.perform(MockMvcRequestBuilders.fileUpload("/gps/upload")
                          .file(uploadedFile).param("userName", "chung"))
                      .andExpect(status().is(200))
                      .andExpect(content().string("success"));
    }
    
    @Test
    public void testUploadMaps2() throws Exception {
    	
    	  File file = new File("D:\\sample2.gpx");
    	  InputStream in = new FileInputStream(file);
    	  byte fileContent[] = new byte[(int)file.length()];
    	  in.read(fileContent);
    	  MockMultipartFile uploadedFile = new MockMultipartFile("file", "filename.txt", "text/plain",fileContent);
          MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
          mockMvc.perform(MockMvcRequestBuilders.fileUpload("/gps/upload")
                          .file(uploadedFile).param("userName", "chung"))
                      .andExpect(status().is(200))
                      .andExpect(content().string("success"));
    }
    
    @Test
    public void testGetUser() throws Exception {
    	 MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         mockMvc.perform(MockMvcRequestBuilders.get("/users/find/{name}", "chung"))
                     .andExpect(status().is(200))
                     .andExpect(content().string("chung"));
    }
    
    @Test
    public void testGetLatestPosition() throws Exception {
    	 MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         mockMvc.perform(MockMvcRequestBuilders.get("/users/latestposition/{name}", "chung"))
                     .andExpect(status().is(200))
                     .andExpect(content().string("42.2208895 : -1.4580696"));
    }
    

}
