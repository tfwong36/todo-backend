package com.tatp.restapi.controller;

import com.tatp.restapi.entity.Todo;
import com.tatp.restapi.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("todos")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return new ArrayList<>(todoService.findAll());
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id){
        return todoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createEmployee(@RequestBody Todo todoRequest){
        return todoService.create(todoRequest);
    }

    @PutMapping("/{id}")
    public Todo editTodo(@PathVariable String id, @RequestBody Todo editedTodo){
        return todoService.edit(id, editedTodo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTodo(@PathVariable String id){
        todoService.remove(id);
    }

}
