package com.app.persistence.repository;

import com.app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserCrudRepository extends JpaRepository<UserEntity, Long> {
    //    @Query(value = "SELECT u FROM user WHERE username LIKE ?1", nativeQuery = true)
    @Query(value = "SELECT u from UserEntity u WHERE u.username = ?1")
    Optional<UserEntity> findUserByUsername(String username);
}
