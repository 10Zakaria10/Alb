package com.theodo.albeniz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.model.Tune;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "data")
public class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testGetMusic_whenReceiveConfigurationValues_receiveOkAndExpectedResult() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(2, "You cant take me"  ,"Brayn Adams" ))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'id':2,'author':'Brayn Adams','title':'You cant take me'},{'id':1,'author':'Michael J.','title':'Thriller'}]"));


    }

    @Test
    public void testUpdateTune_whenIdIsNotTheire_receiveNotFound() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(2, "Thriqdfller", "Mqsdfichael J."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testUpdateTune_whenTuneIsTheir_receiveSuccesfull() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(put("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriqdfller", "Mqsdfichael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Mqsdfichael J.','title':'Thriqdfller'}]"));
    }

    @Test
    public void testDeleteTune_whenIdIsProvidedButNotTheir_receiveOK() throws Exception {

        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(delete("/library/music/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testDeleteTune_whenIdIsProvided_receiveOK() throws Exception {

        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(delete("/library/music/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[]"));
    }

    @Test
    public void testAddTune_whenAuthorIsChantalG_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "proMusic", "Chantal G."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testAddTune_whenTuneAlreadyExist_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testAddTune_whenTitleIsNotProvided_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "", "Michael J."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testAddTune_whenTuneIsProvided_receiveOK() throws Exception {
        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[]"));

        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Tune(1, "Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Michael J.','title':'Thriller'}]"));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testGetMusic_whenQueryIsProvided_receiveOkAndListOfTune() throws Exception {
        mockMvc.perform(get("/library/music?query=thril")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Michael J.','title':'Thriller'}]"));
    }


    @Test
    public void testGetMusic_whenQueryIsNotProvided_receiveOkAndListOfTune() throws Exception {
        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Bruno M.','title':'Uptown Funk'},{'author':'Michael J.','title':'Thriller'}]"));
    }

    @Test
    public void testGetMusic_whenIdIsProvided_receiveIsOkAndUniqueTune() throws Exception {
        mockMvc.perform(get("/library/music/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'title':'Thriller','author':'Michael J.'}"));
    }
}
