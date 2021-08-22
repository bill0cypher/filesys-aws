package com.java.aws.filesys.filesysaws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.aws.filesys.filesysaws.model.enums.EventType;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
    private Integer id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "uploaded", nullable = false)
    private Date modifiedDate;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private File file;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE})
    private User user;

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date uploadedDate) {
        this.modifiedDate = uploadedDate;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
