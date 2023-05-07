package com.example.admin.services.impl;

import com.example.admin.dto.response.StaticCountByIndex;
import com.example.admin.model.Person;
import com.example.admin.model.repository.MessageRepository;
import com.example.admin.model.repository.PersonRepository;
import com.example.admin.model.repository.PostRepository;
import com.example.admin.services.Statistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticImpl implements Statistic {

    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;
    private final PostRepository postRepository;
    @Override
    public Long getCountPeople() {
       return personRepository.getAllCountPeople();
    }

    @Override
    public Long getCountOnlinePeople() {
        return personRepository.getCountOnlinePeople();
    }

    @Override
    public Long getCountMessages() {
       return messageRepository.getAllMessage();
    }

    @Override
    public Long getCountPost() {
        return postRepository.getCountAllPost();
    }

    @Override
    public StaticCountByIndex getStatic() {
        StaticCountByIndex staticCountByIndex = new StaticCountByIndex();
        staticCountByIndex.setCountMessages(getCountMessages());
        staticCountByIndex.setCountPeople(getCountPeople());
        staticCountByIndex.setCountOnlinePeople(getCountOnlinePeople());
        staticCountByIndex.setCountPosts(getCountPost());
        return staticCountByIndex;
    }


}
