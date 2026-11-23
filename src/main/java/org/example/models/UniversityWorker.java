package org.example.models;

public class UniversityWorker {
    String department;
    String name;

    public UniversityWorker(String department, String name){
        this.department = department;
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
}
