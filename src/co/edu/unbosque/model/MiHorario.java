package co.edu.unbosque.model;

import co.edu.unbosque.model.persistence.AssignmentDAO;
import co.edu.unbosque.model.persistence.Binaries;
import co.edu.unbosque.model.persistence.ScheduleProperties;

import java.awt.*;
import java.io.IOException;

/**
 * @author Gabriel Blanco - Rabih Souiden
 */
public class MiHorario {
    private AssignmentDAO assignment = new AssignmentDAO();
    private Binaries binaries = new Binaries();
    private Student student;
    private ScheduleProperties properties = new ScheduleProperties();

    public String loadData() {
        String json = "";
        try {
            json = binaries.read();
            var separatedJson = json.split("-");
            assignment.restoreArray(separatedJson[0]);
        } catch (ArrayIndexOutOfBoundsException | IOException out) {
            try {
                binaries.write(assignment.saveArray());
            } catch(OutOfMemoryError | IOException om) {
                System.err.println("Can't write file! Cause: "+om.getCause());
            }
        }
        return json;
    }

    public String loadProfile() {
        String json = "";
        try {
            json = properties.readProperties();
            var separatedJson = json.split(",");
            student = new Student(separatedJson[0],
                    separatedJson[1],
                    Integer.parseInt(separatedJson[2]),
                    Integer.parseInt(separatedJson[3]),
                    Integer.parseInt(separatedJson[4]),
                    Boolean.getBoolean(separatedJson[5]),
                    Double.parseDouble(separatedJson[6]));
        } catch (IOException io) {
            json = "";
        }
        return json;
    }

    public void newAssignment(String name, String teacher, int credits, int periods, double[] grades, int hour, int[] days, boolean obligatory, Color color) {
        assignment.createAssignment(name, teacher, credits, periods, grades, hour, days, obligatory, color);
        try {
            binaries.write(assignment.saveArray());
        } catch (IOException io) {
            System.err.println("Can't write file! Cause: "+io.getCause());
        }
    }

    public void removeAssignment(int assignmentCode) {
        assignment.deleteAssignment(assignmentCode);
    }

    public void updateAssignment(int assignmentCode, String name, String teacher, int credits, int periods, double[] grades, int hour, int[] days, boolean obligatory, Color color) {

    }

    public String viewAssignment(int assignmentCode) {
        return null;
    }

    public int searchAssignmentCode(String name) {
        int code = 0;
        for (int i = 0; i < assignment.getAssignmentSize(); i++) {
            var nameCheck = assignment.readAssignment(i).split("\n");
            var assignment = nameCheck[0].split(": ");
            if(assignment[1] == name) {
                code = i;
            }
        }
        return code;
    }

    public ScheduleProperties getProperties() {
        return properties;
    }
}
