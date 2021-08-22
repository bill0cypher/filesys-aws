package com.java.aws.filesys.filesysaws.model;

import com.java.aws.filesys.filesysaws.model.enums.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
public class BaseEntity {

    @Id
    @SequenceGenerator(name = "base_id_seq", sequenceName = "base_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "base_id_seq", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    @CreatedDate
    private Date created;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private Date lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'ACTIVE'")
    private Status status;

    public BaseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
