package com.example.vinutna.notekeeper;

import java.util.Date;

/**
 * Created by Lata on 27-02-2017.
 */

public class Notes {
    private long id;
    private String note,priority,status;
    Date updateTime;

    public Notes(String note, String priority, String status) {
        this.note = note;
        this.priority = priority;
        this.status = status;
    }

    public Notes(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
