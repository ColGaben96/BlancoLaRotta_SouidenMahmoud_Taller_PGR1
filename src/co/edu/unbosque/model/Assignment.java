package co.edu.unbosque.model;

import java.awt.*;

public abstract class Assignment {
    public abstract String getName();
    public abstract String getTeacher();
    public abstract int getCredits();
    public abstract int getPeriods();
    public abstract double[] getGrades();
    public abstract Color getColor();
    public abstract int getHour();
    public abstract int[] getDays();
    public abstract boolean isObligatory();
}
