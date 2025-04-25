package com.andreidodu.aitodolist.service;

import com.andreidodu.aitodolist.constants.ApplicationConstants;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ChatMemoryStore implements dev.langchain4j.store.memory.chat.ChatMemoryStore {

    private final DB db = DBMaker.fileDB(ApplicationConstants.DATABASE_FILENAME).transactionEnable().make();
    private final Map<Integer, String> map = db.hashMap("messages", Serializer.INTEGER, Serializer.STRING).createOrOpen();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String json = map.get((int) memoryId);
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String json = ChatMessageSerializer.messagesToJson(messages);
        map.put((int) memoryId, json);
        db.commit();
    }

    @Override
    public void deleteMessages(Object memoryId) {
        map.remove((int) memoryId);
        db.commit();
    }
}
