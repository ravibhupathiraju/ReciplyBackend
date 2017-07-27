package com.lmig.reciply;


import com.lmig.reciply.MealPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

public interface MealPlanRepository extends JpaRepository<MealPlan, Integer> {
	
	List<MealPlan> findByuserID(String userId);
	MealPlan findByplanId(Integer id);
	@Query("SELECT m from MealPlan m where m.userID = :userId and m.weekBeginDate = :date)")
	List<MealPlan> searchWithDate(@Param("userId") String userId,@Param("date") LocalDate weekBeginDate);
	@Query("SELECT m from MealPlan m where m.userID = :userId)")
	List<MealPlan> search(@Param("userId") String userId);
}