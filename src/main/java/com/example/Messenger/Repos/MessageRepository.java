package com.example.Messenger.Repos;

import com.example.Messenger.Models.ChatId;
import com.example.Messenger.Models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Iterable<Message> findByChatid(UUID chatid);
}
