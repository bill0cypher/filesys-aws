package com.java.aws.filesys.filesysaws.repo;

import com.java.aws.filesys.filesysaws.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("from Event e where e.user.id=:id")
    List<Event> getAllByUserId(Integer id);
}
