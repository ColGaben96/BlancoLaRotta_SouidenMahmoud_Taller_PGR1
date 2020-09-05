package co.edu.unbosque.controller;

import co.edu.unbosque.model.MiHorario;
import co.edu.unbosque.view.MainView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;

/**
 * This class is the controller of the application. This is designed to give orders to view and model and has the responsibility for it's communication.
 * @author Gabriel Blanco
 * @version 1.0
 */
public class Controller implements ActionListener {
    private MainView gui = new MainView();
    private MiHorario model = new MiHorario();
    public void start() {
        gui.start(this);
        int status = 0;
        String profile = "";
        try {
            loadAndPrint(model.loadData());
            profile = model.loadProfile();
        } catch (Exception e) {
            status = 1;
        }
        if(profile.equals("")){
            newProfile();
        } else {
            goGUI();
        }
    }
    public void newProfile() {
        gui.getProfile().setVisible(true);
        gui.setVisible(false);

    }
    public void goGUI() {
    gui.setVisible(true);
    }

    public void loadAndPrint(String dataFromModel) {
        var separatedAssignments = dataFromModel.split("\\$\n");
        for (int i = 1; i < separatedAssignments.length; i++) {
            var separatedElements = separatedAssignments[i].split(",");
            int hour = Integer.parseInt(separatedElements[6]);
            var days = separatedElements[7].split(";");
            int[] day = new int[days.length];
            for (int j = 0; j < days.length; j++) {
                day[j] = Integer.parseInt(days[j]);
            }
            String name = separatedElements[1];
            var separatedColors = separatedElements[9].split(";");
            int red = Integer.parseInt(separatedColors[0]);
            int green = Integer.parseInt(separatedColors[1]);
            int blue = Integer.parseInt(separatedColors[2]);
            Color rgb = new Color(red,green,blue);
            for (int j = 0; j < day.length; j++) {
                gui.getSchedule().addOrUpdateAssignment(hour,day[j]+1,name,rgb);
            }
        }
    }

    public void loadUpdateData(int i, int j) {
        if(model.assignmentFound(gui.getSchedule().getSchedule()[i][j].getText(), i, j)) {
            var assignmentCode = model.searchAssignmentCode(gui.getSchedule().getSchedule()[i][j].getText());
            var assignmentDetails = model.viewAssignment(assignmentCode).split(",");
            try {
                var name = assignmentDetails[0];
                var teacher = assignmentDetails[1];
                var credits = assignmentDetails[2];
                var periods = Integer.parseInt(assignmentDetails[3]);
                var grades = assignmentDetails[4].split(";");
                var hour = assignmentDetails[5];
                var days = assignmentDetails[6].split(";");
                int[] day = new int[days.length];
                int l = 0;
                for (int k = 0; k < day.length; k++) {
                    if(days[k] != "0") {
                        day[l] = Integer.parseInt(days[k]);
                        l++;
                    }
                }
                var obligatory = Boolean.getBoolean(assignmentDetails[7]);
                var color = assignmentDetails[8].split(";");
                if(obligatory) {
                    gui.getCrud().getAssignmentType().setSelectedIndex(1);
                } else {
                    gui.getCrud().getAssignmentType().setSelectedIndex(2);
                }
                gui.getCrud().getAssignmentName().setText(name);
                gui.getCrud().getAssignmentTeacher().setText(teacher);
                gui.getCrud().getCredits().setText(credits);
                for (int k = 0; k < periods; k++) {
                    gui.getCrud().getGradesPanel().addPeriod();
                    for (int l1 = 0; l1 < grades.length; l1++) {
                        gui.getCrud().getGradesPanel().updatePeriod(k,Double.parseDouble(grades[l1]));
                    }
                }
                gui.getCrud().getHour().setSelectedIndex(Integer.parseInt(hour));
                for (int k = 0; k < gui.getCrud().getDayCbox().length; k++) {
                    for (int l1 = 0; l1 < day.length; l1++) {
                        if(day[l1] != 0) {
                            gui.getCrud().getDayCbox()[l1].setSelected(true);
                        }
                    }
                }
                int red = Integer.parseInt(color[0]);
                int green = Integer.parseInt(color[1]);
                int blue = Integer.parseInt(color[2]);
                gui.getCrud().getColorPicker().setBackground(new Color(red, green, blue));
            } catch (IndexOutOfBoundsException out) {

            }
        }
    }

    public void saveAssignPrint() {
        var wholeCol = gui.getCrud().getDayCbox();
        String col = "";
        for (int i = 0; i < wholeCol.length; i++) {
            if(wholeCol[i].isSelected()) {
                col += i+",";
            }
        }
        if(col.equals("")) {
            gui.getAlerts().output("Debes seleccionar al menos un día","Error",JOptionPane.ERROR_MESSAGE);
        }
        var selectedCol = col.split(",");
        if(selectedCol.length > 1) {
        	if(gui.getCrud().getAssignmentType().getSelectedIndex() == 1) {
                model.newAssignment(gui.getCrud().getAssignmentName().getText(),
                        gui.getCrud().getAssignmentTeacher().getText(),
                        Integer.parseInt(gui.getCrud().getCredits().getText()),
                        gui.getCrud().getGradesPanel().getPeriodCount(),
                        gui.getCrud().getGradesPanel().getGrades(),
                        gui.getCrud().getHour().getSelectedIndex(),
                        gui.getCrud().getSelectedDays(),
                        true,
                        gui.getCrud().getColorPicker().getBackground());
            }
            if(gui.getCrud().getAssignmentType().getSelectedIndex() == 2) {
                model.newAssignment(gui.getCrud().getAssignmentName().getText(),
                        gui.getCrud().getAssignmentTeacher().getText(),
                        Integer.parseInt(gui.getCrud().getCredits().getText()),
                        gui.getCrud().getGradesPanel().getPeriodCount(),
                        gui.getCrud().getGradesPanel().getGrades(),
                        gui.getCrud().getHour().getSelectedIndex(),
                        gui.getCrud().getSelectedDays(),
                        false,
                        gui.getCrud().getColorPicker().getBackground());
            }
            if(gui.getCrud().getAssignmentType().getSelectedIndex() == 0) {
                gui.getAlerts().output("Debes seleccionar un tipo para la asignatura","Error",JOptionPane.ERROR_MESSAGE);
            }
            for (int i = 0; i < selectedCol.length; i++) {
                gui.getSchedule().addOrUpdateAssignment(gui.getCrud().getHour().getSelectedIndex(),Integer.parseInt(selectedCol[i])+1,gui.getCrud().getAssignmentName().getText(),gui.getCrud().getColorPicker().getBackground());
            }
        }
        if(selectedCol.length == 1) {
            gui.getSchedule().addOrUpdateAssignment(gui.getCrud().getHour().getSelectedIndex(),Integer.parseInt(selectedCol[0])+1,gui.getCrud().getAssignmentName().getText(),gui.getCrud().getColorPicker().getBackground());
        }
    }

    public void newAssignment(int i, int j) {
        gui.getCrud().setVisible(true);
        gui.getCrud().getHour().setSelectedIndex(i);
        gui.getCrud().getDayCbox()[j-1].setSelected(true);
        if(gui.getSchedule().getSchedule()[i][j].getText().contains(":")) {
            try {
                var data = gui.getSchedule().getSchedule()[i][j].getText().split(":");
                gui.getCrud().getAssignmentName().setText(data[0]);
                gui.getCrud().getAssignmentTeacher().setText(data[1]);
                if(data[2] != null) {
                    gui.getCrud().getCredits().setText(data[2]);
                }
                if(data[3] != null) {
                    gui.getCrud().getAssignmentType().setSelectedIndex(Integer.parseInt(data[3]));
                }
            } catch (IndexOutOfBoundsException outofbounds) {

            }
        }
        /*Validation if user is just using the quick add shortcut*/
        if((gui.getSchedule().getSchedule()[i][j].getText() != null || !(gui.getSchedule().getSchedule()[i][j].getText().equals("")) && !(gui.getSchedule().getSchedule()[i][j].getText().contains(":")))) {
            var data = gui.getSchedule().getSchedule()[i][j].getText();
            gui.getCrud().getAssignmentName().setText(data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*gui.Schedule action event*/
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                if(e.getActionCommand().equals(gui.getSchedule().HORARIO[i][j])) {
                    gui.getCrud().restoreDefaultData();
                    loadUpdateData(i,j);
                    newAssignment(i,j);
                }
                if(e.getActionCommand().equals(gui.getSchedule().ADDASSIGNMENT)) {
                    gui.getCrud().restoreDefaultData();
                    newAssignment(i,j);
                }
                if(e.getActionCommand().equals(gui.getSchedule().UPDATEASSIGNMENT)) {

                }
                if(e.getActionCommand().equals(gui.getSchedule().DELETEASSIGNMENT)) {

                }
            }
        }
        /*gui.CRUD*/
        if(e.getActionCommand().equals(gui.getCrud().SELECTCOLOR)) {
            gui.getCrud().getColorPicker().setBackground(gui.getCrud().selectColor());
            gui.getCrud().getColorPicker().setText(gui.getCrud().getRGB());
        }
        if(e.getActionCommand().equals(gui.getCrud().OK)) {
            saveAssignPrint();
            gui.getCrud().setVisible(false);
            gui.getCrud().restoreDefaultData();
        }
        if(e.getActionCommand().equals(gui.getCrud().getGradesPanel().ADDPERIOD)) {
            gui.getCrud().getGradesPanel().addPeriod();
        }
        if(e.getActionCommand().equals(gui.getCrud().getGradesPanel().DELETEPERIOD)) {
            gui.getCrud().getGradesPanel().removePeriod();
        }
        if(e.getActionCommand().equals(gui.getCrud().CANCEL)) {
            gui.getCrud().setVisible(false);
            gui.getCrud().restoreDefaultData();
        }
        /*gui.getProfile()*/
        if(e.getActionCommand().equals(gui.getProfile().FINISHOOBE)) {
            if(gui.getProfile().getComboScholarship().getSelectedIndex() == 0) {
                model.createProfile(gui.getProfile().getTxName().getText(),
                        gui.getProfile().getTxCareer().getText(),
                        Integer.parseInt(gui.getProfile().getTxSemester().getText()),
                        gui.getProfile().getComboStatus().getSelectedIndex(),
                        gui.getProfile().getComboPlan().getSelectedIndex(),
                        true,
                        Double.parseDouble(gui.getProfile().getTxAverage().getText()));
            } else {
                model.createProfile(gui.getProfile().getTxName().getText(),
                        gui.getProfile().getTxCareer().getText(),
                        Integer.parseInt(gui.getProfile().getTxSemester().getText()),
                        gui.getProfile().getComboStatus().getSelectedIndex(),
                        gui.getProfile().getComboPlan().getSelectedIndex(),
                        false,
                        Double.parseDouble(gui.getProfile().getTxAverage().getText()));
            }
            DecimalFormat decimal = new DecimalFormat("###,###.000");
            if(model.getStudent().getMatricula() == 0.00) {
                gui.getAlerts().output("¡Felicidades! Por tener un promedio de "+model.getStudent().getAverage()+" tienes un valor de la matricula cero", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                gui.getAlerts().output("¡Felicidades! Por tener un promedio de "+model.getStudent().getAverage()+" tienes un valor de la matricula de $"+decimal.format(model.getStudent().getMatricula()), "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            gui.getProfile().setVisible(false);
            gui.setVisible(true);
        }
    }
}

/**
 * Class to execute the application
 * @author Gabriel Blanco
 * @version 1.0
 */
class APLMain {
    /**
     * @author Gabriel Blanco
     * @param args
     */
    public static void main(String[] args) {
        Controller c = new Controller();
        c.start();
    }
}
