package com.tdurko.parking;

import com.tdurko.parking.model.Parking;
import com.tdurko.parking.repository.ParkingRepository;
import com.tdurko.parking.service.ParkingService;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


/**
 * Created by Tomek on 2018-05-15.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingApplication.class)
@WebAppConfiguration
public class ParkingControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private ParkingRepository parkingRepository;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<Parking> parkingList = new ArrayList<>();

    @Autowired

    private ParkingService parkingService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.parkingRepository.deleteAllInBatch();
        this.parkingList.add(parkingService.addParkingMeter(new Parking(true)));
        this.parkingList.add(parkingService.addParkingMeter(new Parking(false)));
    }

    @Test
    public void readSingleParkingMeter() throws Exception {
        mockMvc.perform(get("/parking/"
                + this.parkingList.get(0).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void startParkingMeter() throws Exception {
        String bookmarkJson = json(new Parking(true));

        this.mockMvc.perform(post("/parking/start")
                .contentType(contentType)
                .content(bookmarkJson))
                .andExpect(status().isOk());
    }

    @Test
    public void stopParkingMeter() throws Exception {
        mockMvc.perform(put("/parking/stop"
                + this.parkingList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.parkingList.get(0).getId())))
                .andExpect(jsonPath("$.vip", is(this.parkingList.get(0).isVip())))
                .andExpect(jsonPath("$.parks", is(false)));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

