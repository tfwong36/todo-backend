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
        todoRepository.save(new Todo("memo1", false));
        todoRepository.save(new Todo("memo2", false));
        todoRepository.save(new Todo("memo3", false));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("memo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].done").value(false));
    }

    @Test
    void should_return_todo_item_when_perform_post_create_given_todo_item() throws Exception {
        //given
        String newTodo="{\"content\":\"Post memo\",\"done\":false}";

        //when
        //then
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTodo))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Post memo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
        assertEquals(1, todoRepository.findAll().size());
    }

    @Test
    void should_return_todo_item_when_perform_put_to_update_content_given_todo_item_and_new_content() throws Exception {
        //given
        todoRepository.save(new Todo("memo1", false));
        String updateTodo="{\"content\": \"Post memo\"}";

        //when
        //then
        mockMvc.perform(put("/todos/" + todoRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Post memo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));

    }
    @Test
    void should_return_todo_item_when_perform_put_to_update_done_given_todo_item_and_new_content() throws Exception {
        //given
        todoRepository.save(new Todo("memo1", false));
        String updateTodo="{\"done\":true}";

        //when
        //then
        mockMvc.perform(put("/todos/" + todoRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("memo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(true));

    }

}
