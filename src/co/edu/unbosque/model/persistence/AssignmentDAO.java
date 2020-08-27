package co.edu.unbosque.model.persistence;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Gabriel Blanco
 * @version 1.0
 */
public class AssignmentDAO {
    private ArrayList<AssignmentDTO>  assignment = new ArrayList<AssignmentDTO>();
    public void createAssignment(String name, String teacher, int credits, int periods, double[] grades, int hour, int[] days, boolean obligatory, Color color) {
        AssignmentDTO newAssignment = new AssignmentDTO(name, teacher, credits, periods, grades, hour, days, obligatory, color);
        assignment.add(newAssignment);
    }
    public String readAssignment(int asgnCode) {
        String myAssignment = "";
        myAssignment += "Name: "+assignment.get(asgnCode).getName()+",\n";
        myAssignment += "Teacher: "+assignment.get(asgnCode).getTeacher()+",\n";
        myAssignment += "Credits: "+assignment.get(asgnCode).getCredits()+",\n";
        myAssignment += "Periods: "+assignment.get(asgnCode).getPeriods()+",\n";
        String gradesStr = "";
        for (int i = 0; i < assignment.get(asgnCode).getGrades().length; i++) {
            gradesStr += assignment.get(asgnCode).getGrades()[i]+"; ";
        }
        myAssignment += "Grades: "+gradesStr+",\n";
        myAssignment += "Hour: "+assignment.get(asgnCode).getHour()+"\n";
        String daysStr = "";
        for (int i = 0; i < assignment.get(asgnCode).getDays().length; i++) {
            daysStr += assignment.get(asgnCode).getDays()[i]+"; ";
        }
        myAssignment += "Days: "+daysStr+",\n";
        myAssignment += "Obligatory: "+assignment.get(asgnCode).isObligatory()+",\n";
        myAssignment += "Color: "+assignment.get(asgnCode).getColor()+",\n";
        return myAssignment;
    }
    public void updateAssignment(int asgnCode, String name, String teacher, int credits, int periods, double[] grades, int hour, int[] days, boolean obligatory, Color color) {
        assignment.get(asgnCode).setName(name);
        assignment.get(asgnCode).setTeacher(teacher);
        assignment.get(asgnCode).setCredits(credits);
        assignment.get(asgnCode).setPeriods(periods);
        assignment.get(asgnCode).setGrades(grades);
        assignment.get(asgnCode).setHour(hour);
        assignment.get(asgnCode).setDays(days);
        assignment.get(asgnCode).setObligatory(obligatory);
        assignment.get(asgnCode).setColor(color);
    }
    public void deleteAssignment(int asgnCode) {
        assignment.remove(asgnCode);
    }

    public String[] saveArray() {
        String array = "";
        for (int j = 0; j < assignment.size(); j++) {
            array += assignment.get(j).getName()+",";
            array += assignment.get(j).getTeacher()+",";
            array += assignment.get(j).getCredits()+",";
            array += assignment.get(j).getPeriods()+",";
            String gradesStr = "";
            for (int i = 0; i < assignment.get(j).getGrades().length; i++) {
                gradesStr += assignment.get(j).getGrades()[i]+";";
            }
            array += gradesStr+",";
            array += assignment.get(j).getHour()+",";
            String daysStr = "";
            for (int i = 0; i < assignment.get(j).getDays().length; i++) {
                daysStr += assignment.get(j).getDays()[i]+";";
            }
            array += daysStr+",";
            array += assignment.get(j).isObligatory()+",";
            int red = assignment.get(j).getColor().getRed();
            int green = assignment.get(j).getColor().getGreen();
            int blue = assignment.get(j).getColor().getBlue();
            array += red+";"+green+";"+blue+",";
            array += "-n";
        }
        String[] savedArray = array.split("-n");
        return savedArray;
    }

    public void restoreArray(String separatedJson) {
        var theAssignments = separatedJson.split("\\$");
        for (int i = 0; i < theAssignments.length-1; i++) {
            var restoredArray = theAssignments[i].split(",");
            String name = restoredArray[1];
            String teacher = restoredArray[2];
            int credits = Integer.parseInt(restoredArray[3]);
            int periods = Integer.parseInt(restoredArray[4]);
            var restoredDouble = restoredArray[5].split(";");
            double[] grades = new double[restoredDouble.length];
            for (int j = 0; j < periods; j++) {
                grades[j] = Double.parseDouble(restoredDouble[j]);

            }
            int hour = Integer.parseInt(restoredArray[6]);
            var restoredDays = restoredArray[7].split(";");
            int[] days = new int[restoredDays.length];
            for (int j = 0; j < restoredDays.length; j++) {
                days[j] = Integer.parseInt(restoredDays[j]);
            }
            boolean obligatory = Boolean.getBoolean(restoredArray[8]);
            var restoredColors = restoredArray[9].split(";");
            int red = Integer.parseInt(restoredColors[0]);
            int green = Integer.parseInt(restoredColors[1]);
            int blue = Integer.parseInt(restoredColors[2]);
            Color color = new Color(red,green,blue);
            createAssignment(name, teacher, credits, periods, grades, hour, days, obligatory, color);
        }
    }

    public int getAssignmentSize() {
        return assignment.size();
    }
}
