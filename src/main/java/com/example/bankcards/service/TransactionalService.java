package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository taskRepository;

    @Transactional
    public void save(User user){
        userRepository.save(user);
    };

    @Transactional
    public void delete(User user){
        userRepository.delete(user);
    };

    @Transactional
    public void save(Card card){
        taskRepository.save(card);
    };

    @Transactional
    public void delete(Card card){
        taskRepository.delete(card);
    };



}
