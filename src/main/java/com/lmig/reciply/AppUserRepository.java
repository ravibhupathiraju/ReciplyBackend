package com.lmig.reciply;


import com.lmig.reciply.AppUser;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	AppUser findById(Integer id);
	AppUser findByUserIdAndPassword(String userid, String password);
	AppUser findByUserId(String userid);

}
