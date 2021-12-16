package com.tatp.restapi.service;

import com.tatp.restapi.entity.Todo;
import com.tatp.restapi.exception.NoTodoItemFoundException;
import com.tatp.restapi.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    public Todo findById(String id) {
        return todoRepository.findById(id).orElseThrow(NoTodoItemFoundException::new);
    }

    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo remove(String id) {
        todoRepository.findById(id).orElseThrow(NoTodoItemFoundException::new);
        todoRepository.deleteById(id);
        return null;
    }

    public Todo edit(String id, Todo editedTodo){
        Todo todo = todoRepository.findById(id).orElseThrow(NoTodoItemFoundException::new);
        if (editedTodo.getContent() != null)
            todo.setContent(editedTodo.getContent());
        if (editedTodo.getDone() != null)
            todo.setDone(editedTodo.getDone());
        return todoRepository.save(todo);
    }
}
