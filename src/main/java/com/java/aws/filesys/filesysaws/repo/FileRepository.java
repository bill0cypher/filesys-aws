package com.java.aws.filesys.filesysaws.repo;

import com.java.aws.filesys.filesysaws.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    @Override
    @Query("from File f where f.status = 'ACTIVE'")
    List<File> findAll();

    @Query("from File f where f.user.id =:userId and f.status = 'ACTIVE'")
    List<File> findByUserId(@Param("userId") Integer userId);
}
