package com.example.Messenger;

import com.example.Messenger.Models.ChatId;
import com.example.Messenger.Models.Message;
import com.example.Messenger.Models.User;
import com.example.Messenger.Repos.ChatIdRepository;
import com.example.Messenger.Repos.MessageRepository;
import com.example.Messenger.Repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatIdRepository chatIdRepository;
    @Autowired
    private MessageRepository messageRepository;
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

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("warning", "");
        return "index";
    }

    @PostMapping("/")
    public String login(@RequestParam String phone, @RequestParam String password, Model model, HttpServletRequest request){
        User user = userRepository.findByPhoneAndPassword(phone, password);
        if(user != null){
            request.getSession().setAttribute("userid", user.getUserid());
            return "redirect:/chat";
        }
        else{
            model.addAttribute("warning", "Invalid username or password");
            return "index";
        }
    }

    @GetMapping("/registration")
    public String registrationTemplate(Model model){
        model.addAttribute("warning", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam String username,
            @RequestParam String phone,
            @RequestParam String password1,
            @RequestParam String password2,
            Model model,
            HttpServletRequest request)
    {
        if(password1.equals(password2)){
            User user = userRepository.findByPhone(phone);
            if(user != null){
               model.addAttribute("warning", "This phone number already taken");
               return "registration";
            }
            else{
                UUID userid = UUID.randomUUID();
                User newUser = new User(userid, phone, username, password1);
                userRepository.save(newUser);
                request.getSession().setAttribute("userid", userid);
                return "redirect:/chat";
            }
        }
        else{
            model.addAttribute("warning", "Password mismatch");
            return "registration";
        }
    }

    @GetMapping("/chat")
    public String chat(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("userid") != null){
            model.addAttribute("warning", "");
            return "chat";
        }
        else return "redirect:";
    }

    @GetMapping("/create_chat")
    public String createChatTemplate(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("userid") != null){
            model.addAttribute("warning", "");
            return "createChat";
        }
        else return "redirect:";
    }

    @PostMapping("/create_chat")
    public String createChat(@RequestParam String phone, Model model, HttpServletRequest request){
        User user = userRepository.findByPhone(phone);
        if(user != null){
            UUID chatid = UUID.randomUUID();
            ChatId firstRecord = new ChatId(chatid, (UUID)request.getSession().getAttribute("userid"));
            String username = userRepository.findByUserid((UUID)request.getSession().getAttribute("userid")).getUsername();
            ChatId secondRecord = new ChatId(chatid, user.getUserid());
            chatIdRepository.save(firstRecord);
            chatIdRepository.save(secondRecord);
            template.convertAndSend("/messenger/" + user.getUserid().toString(), new Chat(username, chatid.toString(), "chat"));
            return "redirect:/chat";
        }
        else{
            model.addAttribute("warning", "Wrong phone number");
            return "createChat";
        }
    }

    @GetMapping("/userid")
    @ResponseBody
    public String getUserid(HttpServletRequest request){
        UUID userid = (UUID)request.getSession().getAttribute("userid");
        return userid.toString();
    }

    @GetMapping("/get_chats")
    @ResponseBody
    public ArrayList<Chat> getChats(HttpServletRequest request){
        ArrayList<Chat> chats = new ArrayList<>();
        UUID userid = (UUID)request.getSession().getAttribute("userid");
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
}
