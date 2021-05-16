package com.example.demo;

import com.example.demo.model.Person;
import com.example.demo.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void testGetTicketsByObjectMapper() throws Exception {

        TicketRequest ticketRequest =  new TicketRequest();
        Ticket ticket1 =  new Ticket(200, new Person("Some name", "Some other name"));
        Ticket ticket2 =  new Ticket(150, new Person("Name B", "Name C"));
        ticketRequest.setTickets(Arrays.asList(ticket1, ticket2));
        String jsonTicketRequest = new ObjectMapper().writeValueAsString(ticketRequest);
        String jsonTicketRequestByGSon = new Gson().toJson(ticketRequest);

        RequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
           //     .content(jsonTicketRequest);
                .content(jsonTicketRequestByGSon);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));

    }
    @Test
    public void testGetTicketsByLiteral() throws Exception {


        String jsonTicketRequest ="{\n" +
                "  \"tickets\": [\n" +
                "     {\n" +
                "      \"passenger\": {\n" +
                "        \"firstName\": \"Some name\",\n" +
                "        \"lastName\": \"Some other name\"\n" +
                "      },\n" +
                "      \"price\": 200\n" +
                "    },\n" +
                "    {\n" +
                "      \"passenger\": {\n" +
                "        \"firstName\": \"Name B\",\n" +
                "        \"lastName\": \"Name C\"\n" +
                "      },\n" +
                "      \"price\": 150\n" +
                "    }\n" +
                "  ]" +
                "}";


        RequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTicketRequest);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));
    }
    @Test
    public void testRawBody() throws Exception {
        String json = getJSON("src/test/resources/data.json");
        System.out.println("JSON from file "+ json);
        RequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));

    }


    private String getJSON(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }

}
