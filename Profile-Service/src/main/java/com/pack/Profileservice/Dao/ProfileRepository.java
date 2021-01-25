package com.pack.Profileservice.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pack.Profileservice.Entity.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile,String> {

}
