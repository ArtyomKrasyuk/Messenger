package com.example.Messenger;

import com.example.Messenger.Dto.*;
import com.example.Messenger.Models.ChatId;
import com.example.Messenger.Models.Message;
import com.example.Messenger.Models.Session;
import com.example.Messenger.Models.User;
import com.example.Messenger.Repos.ChatIdRepository;
import com.example.Messenger.Repos.MessageRepository;
import com.example.Messenger.Repos.SessionRepository;
import com.example.Messenger.Repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://192.168.0.103", allowCredentials = "true")
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatIdRepository chatIdRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/message")
    public void message(@Payload ChatMessage message){
        Message record = new Message(UUID.fromString(message.getChatid()), UUID.fromString(message.getUserid()), message.getContent(), message.getDate());
        messageRepository.save(record);
        Iterable<ChatId> chats = chatIdRepository.findByChatid(UUID.fromString(message.getChatid()));
        for(ChatId elem: chats){
            template.convertAndSend("/messenger/" + elem.getUserid(), message);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public String login(@RequestBody LogIn logIn, HttpServletRequest request){
        if(request.getSession(false) != null && sessionRepository.existsById(request.getSession().getId())) return "ok";
        User user = userRepository.findByPhoneAndPassword(logIn.getPhone(), logIn.getPassword());
        if(user != null){
            sessionRepository.save(new Session(request.getSession().getId(), user.getUserid()));
            return "ok";
        }
        else return "Invalid username or password";
    }

    @PostMapping("/registration")
    @ResponseBody
    public String registration(@RequestBody Registration reg, HttpServletRequest request) {
        if(request.getSession(false) != null && sessionRepository.existsById(request.getSession().getId())) return "ok";
        User user = userRepository.findByPhone(reg.getPhone());
        if(user != null){
            return "This phone number already taken";
        }
        else{
            UUID userid = UUID.randomUUID();
            while(userRepository.existsByUserid(userid)) userid = UUID.randomUUID();
            User newUser = new User(userid, reg.getPhone(), reg.getUsername(), reg.getPassword1());
            userRepository.save(newUser);
            sessionRepository.save(new Session(request.getSession().getId(), userid));
            return "ok";
        }
    }

    @PostMapping("/create_chat")
    @ResponseBody
    public String createChat(@RequestBody CreateChat phone, HttpServletRequest request){
        if(request.getSession(false) == null || !sessionRepository.existsById(request.getSession().getId())) return "redirect";
        User user = userRepository.findByPhone(phone.getPhone());
        if(user != null){
            UUID chatid = UUID.randomUUID();
            UUID userid = sessionRepository.findById(request.getSession().getId()).get().getUserId();
            while(chatIdRepository.existsByChatid(chatid)) chatid = UUID.randomUUID();
            ChatId firstRecord = new ChatId(chatid, userid);
            String username = userRepository.findByUserid(userid).getUsername();
            ChatId secondRecord = new ChatId(chatid, user.getUserid());
            chatIdRepository.save(firstRecord);
            chatIdRepository.save(secondRecord);
            template.convertAndSend("/messenger/" + user.getUserid().toString(), new Chat(username, chatid.toString(), "chat"));
            return "ok";
        }
        else return "Wrong phone number";
    }

    @GetMapping("/userid")
    @ResponseBody
    public String getUserid(HttpServletRequest request){
        return sessionRepository.existsById(request.getSession().getId()) ? sessionRepository.findById(request.getSession().getId()).get().getUserId().toString() : null;
    }

    @GetMapping("/get_chats")
    @ResponseBody
    public ArrayList<Chat> getChats(HttpServletRequest request){
        ArrayList<Chat> chats = new ArrayList<>();
        if(request.getSession(false) == null || !sessionRepository.existsById(request.getSession().getId())) return chats;
        UUID userid = sessionRepository.findById(request.getSession().getId()).get().getUserId();
        Iterable<ChatId> chatswithuser = chatIdRepository.findByUserid(userid);
        for(ChatId chat : chatswithuser){
            Iterable<ChatId> chatswithchatid = chatIdRepository.findByChatid(chat.getChatid());
            for(ChatId chat1 : chatswithchatid){
                if(!chat1.getUserid().toString().equals(userid.toString())){
                    String username = userRepository.findByUserid(chat1.getUserid()).getUsername();
                    chats.add(new Chat(username, chat1.getChatid().toString(), "chat"));
                }
            }
        }
        return chats;
    }

    @GetMapping("/get_messages")
    @ResponseBody
    public ArrayList<ChatMessage> getMessages(@RequestParam String chatid){
        Iterable<Message> records = messageRepository.findByChatid(UUID.fromString(chatid));
        ArrayList<ChatMessage> messages = new ArrayList<>();
        for(Message record: records){
            ChatMessage message = new ChatMessage(
                    record.getUserid().toString(),
                    record.getChatid().toString(),
                    record.getMessage(),
                    record.getDate(),
                    "message"
            );
            messages.add(message);
        }
        return messages;
    }

    @GetMapping("/exit")
    @ResponseBody
    public String  exit(HttpServletRequest request){
        String id = request.getSession().getId();
        if(sessionRepository.existsById(id)) sessionRepository.deleteById(id);
        request.getSession().invalidate();
        return "ok";
    }

    @GetMapping("/check")
    @ResponseBody
    public boolean checkSession(HttpServletRequest request){
        return request.getSession(false) != null && sessionRepository.existsById(request.getSession().getId());
    }
}
