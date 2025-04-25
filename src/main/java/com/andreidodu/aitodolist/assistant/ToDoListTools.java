package com.andreidodu.aitodolist.assistant;

import com.andreidodu.aitodolist.service.MemoryService;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class ToDoListTools {
    private final MemoryService memoryService;

    @Tool("Store a new todo list item string. For Example, when the user asks: 'memorize the following todo: ...'")
    public String store(String todo) {
        log.info("Called store() with todo: {}", todo);
        return memoryService.store(todo);
    }

    @Tool("Returns all items of the todo list. For Example, when the user asks: 'provide me all todos'")
    public String getAll() {
        log.info("Called getAll()");
        return Arrays.toString(memoryService.getAll().toArray());
    }

    @Tool("After retrieved all items, delete that item that is more likely near the users description. " +
            "For Example, when the user asks: 'delete that todo where is said that i should buy the milk'")
    public String remove(String todo) {
        log.info("Called delete()");
        return memoryService.delete(todo);
    }

}
