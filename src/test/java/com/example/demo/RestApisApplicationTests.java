package com.example.demo;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.util.ContactData;
import com.example.demo.util.ContactOperation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApisApplicationTests {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private ContactOperation contactOperation;
    
    private ContactRecord contact;
    
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() throws Exception{
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.contact = new ContactRecord("tushar", "tushar.abhishek@ymail.com", "7717765317");
		this.contactOperation = new ContactOperation(new ContactData());
	}
	
	@Test
    public void searchByEmail() throws Exception {
        mockMvc.perform(get("/contact/search?email="
                + this.contact.getContactEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(this.contact.getContactName())))
                .andExpect(jsonPath("$.contact", is(this.contact.getContactNumber())));
    }
	
	@Test
    public void searchByName() throws Exception {
        mockMvc.perform(get("/contacts/search?name="
                + this.contact.getContactEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(this.contact.getContactEmail())))
                .andExpect(jsonPath("$[0].contact", is(this.contact.getContactNumber())));
    }
	
	@Test
    public void createContact() throws Exception {
        String contactJson = json(this.contact);

        this.mockMvc.perform(post("/contact")
                .contentType(contentType)
                .content(contactJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
