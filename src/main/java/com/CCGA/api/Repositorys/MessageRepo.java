package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findAllBySentFrom(int sentFromID);
    List<Message> findAllBySentTo(int sentToID);
    List<Message> findAllBySentFromAndSentTo(int sentFromID, int sentToID);
}
