package com.example.Messenger.Repos;

import com.example.Messenger.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findByPhoneAndPassword(String phone, String password);
    User findByPhone(String phone);
    User findByUserid(UUID userid);
}
