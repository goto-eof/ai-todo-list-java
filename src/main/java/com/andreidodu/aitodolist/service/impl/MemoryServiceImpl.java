package com.andreidodu.aitodolist.service.impl;

import com.andreidodu.aitodolist.service.MemoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryServiceImpl implements MemoryService {
    private List<String> todoList = new ArrayList<>();

    @Override
    public String store(String messgae) {
        todoList.add(messgae);
        return messgae;
    }

    @Override
    public List<String> getAll() {
        return todoList;
    }

    @Override
    public String delete(String message) {
        this.todoList = todoList.stream().filter(item -> !item.equalsIgnoreCase(message)).toList();
        return message;
    }

}
