package com.andreidodu.aitodolist.service.impl;

import com.andreidodu.aitodolist.assistant.ToDoListAssistant;
import com.andreidodu.aitodolist.assistant.ToDoListTools;
import com.andreidodu.aitodolist.service.ChatMemoryStore;
import com.andreidodu.aitodolist.service.ToDoListService;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoListServiceImpl implements ToDoListService {
    private final OpenAiChatModel openAiChatModel;
    private final ChatMemoryStore chatMemoryStore;
    private final ToDoListTools tools;

    @Override
    public void processMessage(String message) {

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(chatMemoryStore)
                .build();

        ToDoListAssistant assistant = AiServices.builder(ToDoListAssistant.class)
                .chatLanguageModel(openAiChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(tools)
                .build();

        String response = assistant.interact(0, message);
        System.out.println(response);
    }


}
