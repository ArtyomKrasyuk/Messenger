package com.example.Messenger.Repos;

import com.example.Messenger.Models.ChatId;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChatIdRepository extends CrudRepository<ChatId, UUID> {
    Iterable<ChatId> findByUserid(UUID userid);
    Iterable<ChatId> findByChatid(UUID chatid);
}
