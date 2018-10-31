package com.karaush.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karaush.demo.controllers.RecordController;
import com.karaush.demo.models.Record;
import com.karaush.demo.repositories.RecordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories(basePackageClasses = RecordRepository.class)
@ContextConfiguration(classes = {WebAppContext.class})
@WebAppConfiguration
public class DemoApplicationTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void shouldLoadContext() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean(RecordController.class));
	}

	@Test
    @Sql({ "/data-h2.sql"})
    public void shouldInitDBfromScript() throws Exception {

    	    mockMvc.perform(get("/records")).andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].latitude").value(8))
                .andExpect(jsonPath("$[0].longitude").value(83.12856))
                .andExpect(jsonPath("$[0].temperature").value(-48.61942))
                .andExpect(jsonPath("$[9].latitude").value(7))
                .andExpect(jsonPath("$[9].longitude").value(-47.9162))
                .andExpect(jsonPath("$[9].temperature").value(-41.61942));
    }

    @Test
    @Sql({ "/data-h2.sql"})
    public void shouldAddNewRecordAndDeleteLast() throws Exception {

	    Record testRecord = new Record(0,0,0);
	    mockMvc.perform(MockMvcRequestBuilders.post("/records")
            .content(asJsonString(testRecord))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/records")).andExpect(status().isOk()).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$[0].latitude").value(0))
            .andExpect(jsonPath("$[0].longitude").value(0))
            .andExpect(jsonPath("$[0].temperature").value(0));
    }

    @Test
    public void shouldThrowInvalidLatitude() throws Exception {
        Record invalidLatitude = new Record(100,0,0);
        mockMvc.perform(MockMvcRequestBuilders.post("/records").content(asJsonString(invalidLatitude))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldThrowInvalidLongitude() throws Exception {
        Record invalidLongitude = new Record(0,-200,0);
        mockMvc.perform(MockMvcRequestBuilders.post("/records").content(asJsonString(invalidLongitude))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldThrowInvalidTemperature() throws Exception {
        Record invalidTemperature = new Record(0,0,150);
        mockMvc.perform(MockMvcRequestBuilders.post("/records").content(asJsonString(invalidTemperature))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
}
