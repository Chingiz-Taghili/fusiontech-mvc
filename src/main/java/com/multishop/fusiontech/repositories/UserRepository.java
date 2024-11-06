package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UserEntity> findByKeywordInColumnsIgnoreCase(@Param("keyword") String keyword);

    UserEntity findByEmail(String email);

}
