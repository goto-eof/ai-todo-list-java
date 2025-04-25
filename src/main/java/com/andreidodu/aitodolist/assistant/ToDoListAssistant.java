package com.andreidodu.aitodolist.assistant;

import dev.langchain4j.service.*;

public interface ToDoListAssistant {

    @SystemMessage("You are a professional TODO list maker that stores/retrieves/deletes and reads todo items provided by user." +
            "You are friendly, polite and concise. Your aim is to help the user to manage his todo list. " +
            "You need to understand which is the todo that a user wants to store or retrieve and read." +
            "If you don't know the answer, say so.")
    String interact(@MemoryId int memoryId, @UserMessage String message);

}
