package com.tatp.restapi.service;

import com.tatp.restapi.entity.Todo;
import com.tatp.restapi.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;
    @InjectMocks
    TodoService todoService;

    @Test
    void should_return_all_todo_items_when_find_all_todo_items_given_items() {
        //given
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo("Jason", false));
        given(todoRepository.findAll())
                .willReturn(todos);

        //when
        List<Todo> actual = todoService.findAll();

        //then
        assertEquals(1, actual.size());
        assertEquals("Jason", actual.get(0).getContent());
        assertEquals(false, actual.get(0).getDone());
    }

}
