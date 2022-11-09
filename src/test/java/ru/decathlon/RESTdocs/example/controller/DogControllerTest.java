package ru.decathlon.RESTdocs.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import ru.decathlon.RESTdocs.example.model.Dog;
import ru.decathlon.RESTdocs.example.service.DogService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogController.class)
@AutoConfigureRestDocs
class DogControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DogService service;

    @Test
    @DisplayName("[GET: /api/v1]")
    void shouldReturnDogs() throws Exception {
        List<Dog> dogs = List.of(
                new Dog(1L, "Rex", "German Shepard"),
                new Dog(2L, "Milo", "Jack Russel Terrier")
        );
        when(service.getDogs()).thenReturn(dogs);
        this.mock.perform(RestDocumentationRequestBuilders.get("/api/v1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(mapper.writeValueAsString(dogs)))
                .andDo(document("getDogs",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))
                );
    }

    @Test
    @DisplayName("[GET: /api/v1/{dogId}]")
    void shouldReturnOneDogById() throws Exception {
        Dog dog = new Dog(1L, "Rex", "German Shepard");
        when(service.getOneDogById(1L)).thenReturn(dog);
        this.mock.perform(RestDocumentationRequestBuilders.get("/api/v1/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", 1).exists())
                .andExpect(content().json(mapper.writeValueAsString(dog)))
                .andDo(document("getOneDogById",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("id").description("Dog ID for search.")),
                        responseFields(
                                fieldWithPath("id").description("The dog ID."),
                                fieldWithPath("name").description("The dog Name."),
                                fieldWithPath("breed").description("The dog Breed.")
                        ))
                );
    }

    @Test
    @DisplayName("[POST: /api/v1]")
    void shouldReturnCreatedStatusWhenDogSaved() throws Exception {
        Dog dog = new Dog("Sam", "Welsh Terrier");
        doNothing().when(service).saveDog(dog);
        this.mock.perform(RestDocumentationRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(dog)))
                .andExpect(status().isCreated())
                .andDo(document("saveDog",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("The dog ID.").ignored(),
                                fieldWithPath("name").description("The dog Name."),
                                fieldWithPath("breed").description("The dog Breed.")
                        ))
                );
    }

    @Test
    @DisplayName("[DELETE: /api/v1/{dogId}]")
    void shouldReturnNoContentStatusWhenDogDeleted() throws Exception {
        doNothing().when(service).deleteDogById(1L);
        this.mock.perform(RestDocumentationRequestBuilders.delete("/api/v1/{id}", 1))
                .andExpect(status().isNoContent())
                .andDo(document("deleteDogById",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("id").description("ID to delete a dog."))
                ));
    }
}