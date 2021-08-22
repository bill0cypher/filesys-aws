package com.java.aws.filesys.filesysaws.repo;

import com.java.aws.filesys.filesysaws.model.Role;
import com.java.aws.filesys.filesysaws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("update User u set u.fullName=:fullName, u.email=:email, u.roles=:role where u.id =:id")
    int refresh(
            @Param("fullName") String fullName,
            @Param("email") String email,
            @Param("role") Set<Role> role,
            @Param("id")Integer id);

    @Query("from User u where u.username =:username")
    Optional<User> findByUsername(@Param("username") String username);
}
