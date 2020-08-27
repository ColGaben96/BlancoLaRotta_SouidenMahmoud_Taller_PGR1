package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Assignment;

import java.awt.*;

public class AssignmentDTO extends Assignment {

    private String name, teacher;
    private int credits, periods, hour;
    private int[] days;
    private double[] grades;
    private Color color;
    private boolean obligatory;

    public AssignmentDTO(String name, String teacher, int credits, int periods, double[] grades, int hour, int[] days, boolean obligatory, Color color) {
        this.name = name;
        this.teacher = teacher;
        this.credits = credits;
        this.periods = periods;
        this.grades = grades;
        this.hour = hour;
        this.days = days;
        this.obligatory = obligatory;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTeacher() {
        return teacher;
    }

    @Override
    public int getCredits() {
        return credits;
    }

    @Override
    public int getPeriods() {
        return periods;
    }

    @Override
    public double[] getGrades() {
        return grades;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int[] getDays() {
        return days;
    }

    @Override
    public boolean isObligatory() {
        return obligatory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public void setGrades(double[] grades) {
        this.grades = grades;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }
}
