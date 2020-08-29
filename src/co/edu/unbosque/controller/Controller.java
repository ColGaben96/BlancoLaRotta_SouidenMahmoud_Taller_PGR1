package co.edu.unbosque.controller;

import co.edu.unbosque.model.MiHorario;
import co.edu.unbosque.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class is the controller of the application. This is designed to give orders to view and model and has the responsibility for it's communication.
 * @author Gabriel Blanco
 * @version 1.0
 */
public class Controller implements ActionListener {
    private MainView gui = new MainView();
    private MiHorario model = new MiHorario();
    public void start() {
        int status = 0;
        String data = "";
        String profile = "";
        try {
            data = model.loadData();
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
        //gui.setVisible(false);

    }
    public void goGUI() {
    gui.start(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*gui.Schedule action event*/
        //TODO: Change the iterators values from fixed to whatever is in view.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                //TODO: Bring information from model
                if(e.getActionCommand().equals(gui.getSchedule().HORARIO[i][j])) {
                    if(model.assignmentFound(gui.getSchedule().getSchedule()[i][j].getText())) {
                        var assignmentCode = model.searchAssignmentCode(gui.getSchedule().getSchedule()[i][j].getText());
                    }
                    gui.getCrud().restoreDefaultData();
                    gui.getCrud().setVisible(true);
                    gui.getCrud().getHour().setSelectedIndex(i);
                    gui.getCrud().getDayCbox()[j-1].setSelected(true);
                    /*Validation if user changes text from confirmed assignment*/
                    try {
                        if(model.viewAssignment(model.searchAssignmentCode(gui.getSchedule().getSchedule()[i][j].getText())) != gui.getCrud().getAssignmentName().getText()) {

                        }
                    } catch (NullPointerException npe) {

                    }
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
                            break;
                        }
                    }
                    /*Validation if user is just using the quick add shortcut*/
                    if((gui.getSchedule().getSchedule()[i][j].getText() != null || !(gui.getSchedule().getSchedule()[i][j].getText().equals("")) && !(gui.getSchedule().getSchedule()[i][j].getText().contains(":")))) {
                        var data = gui.getSchedule().getSchedule()[i][j].getText();
                        gui.getCrud().getAssignmentName().setText(data);
                    }
                }
            }
        }
        /*gui.CRUD*/
        if(e.getActionCommand().equals(gui.getCrud().SELECTCOLOR)) {
            gui.getCrud().getColorPicker().setBackground(gui.getCrud().selectColor());
            gui.getCrud().getColorPicker().setText(gui.getCrud().getRGB());
        }
        if(e.getActionCommand().equals(gui.getCrud().OK)) {
            var wholeCol = gui.getCrud().getDayCbox();
            String col = "";
            for (int i = 0; i < wholeCol.length; i++) {
                if(wholeCol[i].isSelected()) {
                    col += i+",";
                }
            }
            var selectedCol = col.split(",");
            if(selectedCol.length > 1) {
                for (int i = 0; i < selectedCol.length; i++) {
                    if(gui.getCrud().getAssignmentType().getSelectedIndex() == 1) {
                        model.newAssignment(gui.getCrud().getAssignmentName().getText(),gui.getCrud().getAssignmentTeacher().getText(), Integer.parseInt(gui.getCrud().getCredits().getText()),gui.getCrud().getGradesPanel().getPeriodCount(),gui.getCrud().getGradesPanel().getGrades(),gui.getCrud().getHour().getSelectedIndex(),gui.getCrud().getSelectedDays(),true,gui.getCrud().getColorPicker().getBackground());
                    }
                    if(gui.getCrud().getAssignmentType().getSelectedIndex() == 2) {
                        model.newAssignment(gui.getCrud().getAssignmentName().getText(),gui.getCrud().getAssignmentTeacher().getText(), Integer.parseInt(gui.getCrud().getCredits().getText()),gui.getCrud().getGradesPanel().getPeriodCount(),gui.getCrud().getGradesPanel().getGrades(),gui.getCrud().getHour().getSelectedIndex(),gui.getCrud().getSelectedDays(),false,gui.getCrud().getColorPicker().getBackground());
                    }
                    gui.getSchedule().addOrUpdateAssignment(gui.getCrud().getHour().getSelectedIndex(),Integer.parseInt(selectedCol[i])+1,gui.getCrud().getAssignmentName().getText(),gui.getCrud().getColorPicker().getBackground());
                }
            }
            if(selectedCol.length == 1) {
                gui.getSchedule().addOrUpdateAssignment(gui.getCrud().getHour().getSelectedIndex(),Integer.parseInt(selectedCol[0])+1,gui.getCrud().getAssignmentName().getText(),gui.getCrud().getColorPicker().getBackground());
            }
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
