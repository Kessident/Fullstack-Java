package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Integer> {
}
