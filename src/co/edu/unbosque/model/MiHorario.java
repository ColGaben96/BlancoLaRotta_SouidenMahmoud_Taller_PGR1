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
            var separatedJson = json.split("\\$");
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
        var separatedElements = json.split(",");
        var semester = Integer.parseInt(separatedElements[2]);
        var status = Integer.parseInt(separatedElements[3]);
        var hasScholarship = Boolean.getBoolean(separatedElements[5]);
        var average = Double.parseDouble(separatedElements[6]);
        if(hasScholarship && semester == 1) {
        	if(status == 0) {
        		student.setMatricula(student.VALOR_MATRICULA*0.00);
        	}
        	if(status == 1) {
        		student.setMatricula(student.VALOR_MATRICULA*0.50);
        	}
        }
        if(hasScholarship && semester > 1) {
        	if(average < 3.3) {
        		student.setMatricula(student.VALOR_MATRICULA*0.50);
        		student.setStatus(1);
        	}
        	else if(average >= 3.3) {
        		student.setMatricula(student.VALOR_MATRICULA*0.00);
        	}
        }
        if(!hasScholarship && semester == 1) {
        	student.setMatricula(student.VALOR_MATRICULA);
        }
        if(!hasScholarship && semester > 1) {
        	if(average < 3.5) {
        		student.setMatricula(student.VALOR_MATRICULA);
        	}
        	else if(average >= 3.5 && average < 3.7) {
        		student.setMatricula(student.VALOR_MATRICULA*0.05);
        	}
        	else if(average >= 3.7 && average < 4.0) {
        		student.setMatricula(student.VALOR_MATRICULA*0.20);
        	}
        	else if(average >= 4.0 && average < 4.5) {
        		student.setMatricula(student.VALOR_MATRICULA*0.35);
        	}
        	else if(average >= 4.5 && average <= 5.0) {
        		student.setMatricula(student.VALOR_MATRICULA*0.50);
        	}
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
        assignment.updateAssignment(assignmentCode,name,teacher,credits,periods,grades,hour,days,obligatory,color);
    }

    public void createProfile(String name, String career, int semester, int status, int plan, boolean scholarship, double average) {
        student = new Student(name, career, semester, status, plan, scholarship, average);
        try {
            properties.writeProperties(name, career, semester, status, plan, scholarship, average);
        } catch (IOException e) {
            System.out.println("Could not create file");
        }
        if(scholarship && semester == 1) {
        	if(status == 0) {
        		student.setMatricula(student.VALOR_MATRICULA*0.00);
        	}
        	if(status == 1) {
        		student.setMatricula(student.VALOR_MATRICULA-student.VALOR_MATRICULA*0.50);
        	}
        }
        if(scholarship && semester > 1) {
        	if(average < 3.3) {
        		student.setMatricula(student.VALOR_MATRICULA-student.VALOR_MATRICULA*0.50);
        		student.setStatus(1);
        	}
        	else if(average >= 3.3) {
        		student.setMatricula(student.VALOR_MATRICULA*0.00);
        	}
        }
        if(!scholarship && semester == 1) {
        	student.setMatricula(student.VALOR_MATRICULA);
        }
        if(!scholarship && semester > 1) {
        	if(average < 3.5) {
        		student.setMatricula(student.VALOR_MATRICULA);
        	}
        	else if(average >= 3.5 && average < 3.7) {
        		student.setMatricula(student.VALOR_MATRICULA-student.VALOR_MATRICULA*0.05);
        	}
        	else if(average >= 3.7 && average < 4.0) {
        		student.setMatricula(student.VALOR_MATRICULA-student.VALOR_MATRICULA*0.20);
        	}
        	else if(average >= 4.0 && average < 4.5) {
        		student.setMatricula(student.VALOR_MATRICULA-student.VALOR_MATRICULA*0.35);
        	}
        	else if(average >= 4.5 && average <= 5.0) {
        		student.setMatricula(student.VALOR_MATRICULA-student.VALOR_MATRICULA*0.50);
        	}
        }
    }

    public String viewAssignment(int assignmentCode) {
        return assignment.readAssignment(assignmentCode);
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

    public boolean assignmentFound(String name, int hour, int day) {
        for (int i = 0; i < assignment.getAssignmentSize(); i++) {
            var nameCheck = assignment.readAssignment(i).split("\n");
            var assignment = nameCheck[0].split(": ");
            if(assignment[1] == name) {
                return true;
            }
        }
        return false;
    }

    public ScheduleProperties getProperties() {
        return properties;
    }

	public AssignmentDAO getAssignment() {
		return assignment;
	}

	public Binaries getBinaries() {
		return binaries;
	}

	public Student getStudent() {
		return student;
	}
    
    
}
