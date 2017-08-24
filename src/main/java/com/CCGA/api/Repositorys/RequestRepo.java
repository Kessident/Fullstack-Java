package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Request;
import com.CCGA.api.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepo extends CrudRepository<Request, Integer> {
    List<Request> findAllByUserRequestedEquals(User user);
    Request findByRequestIDAndUserRequested(int requestID, User user);
}
