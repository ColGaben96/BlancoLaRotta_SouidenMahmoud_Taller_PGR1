package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Gabriel Blanco
 * @version 1.0
 */
public class CRUDAssignmentDialog extends JDialog {
    private JLabel lbassignmentType = new JLabel("Tipo de la asignatura"),
            lbassignmentName = new JLabel("Asignatura"),
            lbassignmentTeacher = new JLabel("Docente"),
            lbCredits = new JLabel("Créditos de la asignatura"),
            lbhour = new JLabel("Hora"),
            lbdays = new JLabel("Dias"),
            lbColorPicker = new JLabel("Color");
    private JTextField assignmentName = new JTextField(),
            assignmentTeacher = new JTextField(),
            credits = new JTextField();
    private JComboBox<String> assignmentType = new JComboBox<String>(),
            hour = new JComboBox<String>();
    private JCheckBox[] dayCbox = new JCheckBox[6];
    private JButton ok = new JButton("Confirmar cambios y salir"),
            cancel = new JButton("Salir sin guardar cambios"),
            colorPicker = new JButton(),
            removeAssignment = new JButton("Eliminar asignatura");
    private JPanel initialData = new JPanel(),
            daysChecks = new JPanel(),
            operationsPanel = new JPanel();
    public final String OK = "SAVECRUDCHANGES", CANCEL = "DISCARDCRUDCHANGES", SELECTCOLOR = "CRUDSELECTCOLOR", REMOVE = "REMOVEASSIGNMENT";
    private String[] asgnTypes = {"Selecciona uno","Obligatoria","Electiva"},
        asgnHours = {"Selecciona uno","7:00 - 9:00", "9:00 - 11:00", "11:00 - 13:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00", "20:00 - 22:00"};
    private GradesPanel gradesPanel = new GradesPanel();

    public CRUDAssignmentDialog() {
        load();
        addComponents();
    }

    private void load() {
        setLayout(new GridLayout(4,1));
        assignmentType = new JComboBox<>(asgnTypes);
        hour = new JComboBox<>(asgnHours);
        ok.setActionCommand(OK); cancel.setActionCommand(CANCEL); colorPicker.setActionCommand(SELECTCOLOR); removeAssignment.setActionCommand(REMOVE);
        colorPicker.setBackground(new Color(255,255,255));
        colorPicker.setText(colorPicker.getBackground().toString());
        dayCbox[0] = new JCheckBox("Lunes");
        dayCbox[1] = new JCheckBox("Martes");
        dayCbox[2] = new JCheckBox("Miércoles");
        dayCbox[3] = new JCheckBox("Jueves");
        dayCbox[4] = new JCheckBox("Viernes");
        dayCbox[5] = new JCheckBox("Sábado");
        for (int i = 0; i < dayCbox.length; i++) {
            dayCbox[i].setBackground(new Color(255,255,255));
        }
        setSize(650,600);
        setTitle("Asignatura");
        initialDataConfig();
        daysChecksConfig();
        operationsPanelConfig();
        setBackground(new Color(255,255,255));
        setLocationRelativeTo(null);
    }

    private void initialDataConfig() {
        TitledBorder title = new TitledBorder("Datos de la asignatura");
        initialData.setLayout(new GridLayout(4,2));
        initialData.add(lbassignmentType); initialData.add(assignmentType);
        initialData.add(lbassignmentName); initialData.add(assignmentName);
        initialData.add(lbassignmentTeacher); initialData.add(assignmentTeacher);
        initialData.add(lbCredits); initialData.add(credits);
        initialData.setBorder(title);
        initialData.setBackground(new Color(255,255,255));
    }

    private void daysChecksConfig() {
        TitledBorder title = new TitledBorder("Dia/s y hora de la asignatura");
        JSeparator separator = new JSeparator();
        daysChecks.setLayout(new GridLayout(6,2));
        daysChecks.add(lbdays); daysChecks.add(separator);
        for (int i = 0; i < dayCbox.length; i++) {
            daysChecks.add(dayCbox[i]);
        }
        daysChecks.add(lbhour); daysChecks.add(hour);
        daysChecks.setBorder(title);
        daysChecks.setBackground(new Color(255,255,255));
    }

    private void operationsPanelConfig() {
        TitledBorder title = new TitledBorder("Operaciones sobre la asignatura");
        JSeparator separator = new JSeparator();
        operationsPanel.setBorder(title);
        operationsPanel.setLayout(new GridLayout(2,3));
        operationsPanel.add(separator); operationsPanel.add(lbColorPicker); operationsPanel.add(colorPicker);
        operationsPanel.add(ok); operationsPanel.add(cancel); operationsPanel.add(removeAssignment);
        operationsPanel.setBackground(new Color(255,255,255));
    }

    private void addComponents() {
        add(initialData);
        add(daysChecks);
        add(gradesPanel);
        add(operationsPanel);
    }

    public void restoreDefaultData() {
        assignmentType.setSelectedIndex(0);
        assignmentName.setText("");
        assignmentTeacher.setText("");
        credits.setText("");
        dayCbox[0].setSelected(false);
        dayCbox[1].setSelected(false);
        dayCbox[2].setSelected(false);
        dayCbox[3].setSelected(false);
        dayCbox[4].setSelected(false);
        dayCbox[5].setSelected(false);
        hour.setSelectedIndex(0);
        colorPicker.setBackground(new Color(255,255,255));
        colorPicker.setText(getRGB());
    }

    public String getRGB() {
        var red = colorPicker.getBackground().getRed();
        var green = colorPicker.getBackground().getGreen();
        var blue = colorPicker.getBackground().getBlue();
        if((red < 125 && green < 125 && blue < 125) || (red < 125 && green < 125 && blue < 180) || (red < 125 && blue < 125 && green < 180) || (blue < 125 && green < 125 && red < 180)) {
            colorPicker.setForeground(new Color(255,255,255));
        } else {
            colorPicker.setForeground(new Color(0,0,0));
        }
        return "Red: "+red+"; Green: "+green+"; Blue: "+blue;
    }

    public int[] getSelectedDays() {
        int[] selected = new int[dayCbox.length];
        for (int i = 0; i < dayCbox.length; i++) {
            if(dayCbox[i].isSelected()) {
                selected[i] = i;
            }
        }
        return selected;
    }

    public Color selectColor() {
        return JColorChooser.showDialog(this, "Selecciona un color", new Color(255,255,255));
    }

    public JTextField getAssignmentName() {
        return assignmentName;
    }

    public JTextField getAssignmentTeacher() {
        return assignmentTeacher;
    }

    public JTextField getCredits() {
        return credits;
    }

    public JComboBox<String> getAssignmentType() {
        return assignmentType;
    }

    public JComboBox<String> getHour() {
        return hour;
    }

    public JButton getOk() {
        return ok;
    }

    public JButton getCancel() {
        return cancel;
    }

    public JButton getColorPicker() {
        return colorPicker;
    }

    public JCheckBox[] getDayCbox() {
        return dayCbox;
    }

    public GradesPanel getGradesPanel() {
        return gradesPanel;
    }

    public JButton getRemoveAssignment() {
        return removeAssignment;
    }
}
