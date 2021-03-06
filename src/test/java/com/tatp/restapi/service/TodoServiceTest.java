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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

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
    @Test
    void should_return_a_todo_when_get_todo_given_todo_id() {
        //given
        Todo todo1 = new Todo("memo1",false);
        given(todoRepository.findById(todo1.getId()))
                .willReturn(java.util.Optional.of(todo1));
        //when
        Todo actual = todoService.findById(todo1.getId());
        //then
        assertEquals(todo1, actual);
    }

    @Test
    void should_return_item_when_create_new_todo_item_given_new_todo_item() {
        //given
        Todo todo = new Todo("memo1",false);
        //when
        given(todoRepository.insert(todo))
                .willReturn(todo);
        Todo actual = todoService.create(todo);
        //then
        assertEquals(todo, actual);
    }

    @Test
    void should_delete_todo_when_perform_delete_given_todo_and_id() throws Exception {
        //given
        Todo todo = new Todo("demo1",false);
        given(todoRepository.findById(todo.getId()))
                .willReturn(java.util.Optional.of(todo));
        willDoNothing().given(todoRepository).deleteById(todo.getId());
        //when
        todoService.remove(todo.getId());
        //then
        verify(todoRepository).deleteById(todo.getId());
        assertEquals(0, todoRepository.findAll().size());
    }
    @Test
    void should_return_item_when_update_content_given_new_todo_item_content() {
        //given
        Todo todo = new Todo("memo1",false);
        Todo updatedTodo = new Todo("memo2", false);
        todo.setContent(updatedTodo.getContent());
        given(todoRepository.findById(todo.getId()))
                .willReturn(java.util.Optional.of(todo));
        given(todoRepository.save(updatedTodo))
                .willReturn(todo);
        //when
        Todo actual = todoService.edit(todo.getId(), updatedTodo);
        //then
        assertEquals(updatedTodo.getId(), actual.getId());
    }
}
