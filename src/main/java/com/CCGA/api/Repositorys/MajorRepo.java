package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Major;
import org.springframework.data.repository.CrudRepository;

public interface MajorRepo extends CrudRepository<Major, Integer> {
    Major findByName(String name);
}
