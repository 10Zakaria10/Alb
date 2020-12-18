package com.theodo.albeniz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.database.repositories.TuneRepository;
import com.theodo.albeniz.model.Tune;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "data")
@Transactional
@WithMockUser
public class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;

    //@Autowired
    //TuneRepository tuneRepository;

    //@Before
    //public void cleanUp(){
   //     tuneRepository.deleteAll();
    //}



    public MvcResult setupData(String title , String author ) throws Exception {
        return mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune(title, author))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }


    @Test
    public void testGetMusic_whenReceiveConfigurationValues_receiveOkAndExpectedResult() throws Exception {
        setupData("Thriller","Michael J.");
        setupData("You cant take me","Brayn Adams");
        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Brayn Adams','title':'You cant take me'},{'author':'Michael J.','title':'Thriller'}]"));
    }

    @Test
    public void testUpdateTune_whenIdIsNotTheire_receiveNotFound() throws Exception {
        MvcResult result = setupData("Thriller","Michael J.");
        Tune tune = TestUtils.getObject( result.getResponse().getContentAsString() );

        mockMvc.perform(put("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune(tune.getId()+1, "Thriqdfller", "Mqsdfichael J."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateTune_whenTuneIsTheir_receiveSuccesfull() throws Exception {
        MvcResult result = setupData("Thriller","Michael J.");
        Tune tune = TestUtils.getObject( result.getResponse().getContentAsString() );

        mockMvc.perform(put("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune(tune.getId(),"Thriqdfller", "Mqsdfichael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'id':"+tune.getId()+",'author':'Mqsdfichael J.','title':'Thriqdfller'}]"));
    }

    @Test
    public void testDeleteTune_whenIdIsProvidedButNotTheir_receiveNotFound() throws Exception {
        MvcResult result = setupData("Thriller","Michael J.");
        Tune tune = TestUtils.getObject( result.getResponse().getContentAsString() );

        mockMvc.perform(delete("/library/music/"+tune.getId()+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testDeleteTune_whenIdIsProvided_receiveOK() throws Exception {
        MvcResult result = setupData("Thriller","Michael J.");
        Tune tune = TestUtils.getObject( result.getResponse().getContentAsString() );

        mockMvc.perform(delete("/library/music/"+tune.getId())
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
    public void testAddTuneV2_whenGroupeValidationIsMandayoryDate_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/v2/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune("proMusic", "ProAuthor."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testAddTune_whenGroupeValidationIsIsMandayoryDate_receiveSuccesful() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune("proMusic", "ProAuthor."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void testAddTune_whenAuthorIsChantalG_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune("proMusic", "Chantal G."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testAddTune_whenTuneAlreadyExist_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune("Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune("Thriller", "Michael J."))))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testAddTune_whenTitleIsNotProvided_receiveBadRequest() throws Exception {
        mockMvc.perform(post("/library/music")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(new Tune("", "Michael J."))))
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

        setupData("Thriller","Michael J.");

        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Michael J.','title':'Thriller'}]"));

    }

    @Test
    public void testGetMusic_whenQueryIsProvided_receiveOkAndListOfTune() throws Exception {
        setupData("Thriller","Michael J.");
        mockMvc.perform(get("/library/music?query=thril")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Michael J.','title':'Thriller'}]"));
    }


    @Test
    public void testGetMusic_whenQueryIsNotProvided_receiveOkAndListOfTune() throws Exception {
        setupData("Thriller","Michael J.");
        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Michael J.','title':'Thriller'}]"));
    }

    @Test
    public void testGetMusic_whenIdIsProvided_receiveIsOkAndUniqueTune() throws Exception {
        MvcResult result = setupData("Thriller","Michael J.");
        Tune tune = TestUtils.getObject( result.getResponse().getContentAsString() );

        mockMvc.perform(get("/library/music/"+tune.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'title':'Thriller','author':'Michael J.'}"));
    }

    @Test
    public void testGetMusicByAuthor_whenAuthorIsProvided_receiveIsOkAListOfTunes() throws Exception {
        setupData("Thriller","Michael J.");
        setupData("Black or white","Michael J.");

        mockMvc.perform(get("/library/musicByAuthor?author=Michael J.")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'author':'Michael J.','title':'Thriller'},{'author':'Michael J.','title':'Black or white'}]"));
    }
}
