package com.cter.entity;

import java.util.Date;

public class Student {
    private String name;
    private String teacher;
    private Date time;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
	@Override
	public String toString() {
		return "Student [name=" + name + ", teacher=" + teacher + ", time=" + time + "]";
	}
    
    
}
