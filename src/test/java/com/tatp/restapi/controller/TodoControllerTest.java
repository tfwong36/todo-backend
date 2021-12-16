package com.tatp.restapi.controller;

import com.tatp.restapi.entity.Todo;
import com.tatp.restapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    void cleanRepository(){
        todoRepository.deleteAll();
    }
    @Test
    void should_get_all_todo_items_when_perform_get_given_todo_items() throws Exception {
        //given
        todoRepository.save(new Todo("memo1"));
        todoRepository.save(new Todo("memo2"));
        todoRepository.save(new Todo("memo3"));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("memo2"));
    }


    @Test
    void should_return_todo_item_when_perform_post_create_given_todo_item() throws Exception {
        //given
        String newTodo="{\"content\": \"Post memo\"}";

        //when
        //then
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTodo))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Post memo"));
        assertEquals(1, todoRepository.findAll().size());
    }
}
