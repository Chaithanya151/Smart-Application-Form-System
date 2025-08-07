package com.LoginApplicationForm.ApplicationForm.repositry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.LoginApplicationForm.ApplicationForm.entity.Application;

@Repository
public interface ApplicationRepositry extends CrudRepository<Application, Integer> {

}
