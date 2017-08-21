package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Message;
import com.CCGA.api.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findAllBySentFrom(User sentFrom);
    List<Message> findAllBySentTo(User sentTo);
    List<Message> findAllBySentFromAndSentTo(User sentFrom, User sentTo);
}
