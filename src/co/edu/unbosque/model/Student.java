package co.edu.unbosque.model;

public class Student {
    private String name, career;
    private int semester, status, plan;
    private boolean scholarship;
    private double average;

    public Student(String name, String career, int semester, int status, int plan, boolean scholarship, double average) {
        this.name = name;
        this.career = career;
        this.semester = semester;
        this.status = status;
        this.plan = plan;
        this.scholarship = scholarship;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public boolean isScholarship() {
        return scholarship;
    }

    public void setScholarship(boolean scholarship) {
        this.scholarship = scholarship;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
