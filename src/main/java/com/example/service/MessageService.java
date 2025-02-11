package com.example.service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message){
        if(message.getMessageText() == null || message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Message text has to be under 255 characters and not be blank");
        }
        if(message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())){
            throw new IllegalArgumentException("posted by must refer to an existing user");
        }
        return messageRepository.save(message);

    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageId(Integer messageId){
        return messageRepository.findById(messageId);
    }

    public int deleteMessageById(Integer messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessage(Integer messageId, String newText){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()){
            throw new IllegalArgumentException("Message does not exist");
        }
        if(newText == null || newText.trim().isEmpty() || newText.length() > 255){
            throw new IllegalArgumentException("Message text must be under 255 characters and not be blank");
        }
        Message message = optionalMessage.get();
        message.setMessageText(newText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
