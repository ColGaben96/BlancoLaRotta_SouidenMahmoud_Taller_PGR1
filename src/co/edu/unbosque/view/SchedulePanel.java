package co.edu.unbosque.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This panel's purpose is only to paint and do all the CRUD actions on data.
 * @author Gabriel BLanco
 * @version 1.0
 */
public class SchedulePanel extends JPanel {
    private String[] days = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado"}, hours = {"7:00 - 9:00", "9:00 - 11:00", "11:00 - 13:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00", "20:00 - 22:00"};
    private JTextField[][] schedule = new JTextField[hours.length+1][days.length];
    public final String[][] HORARIO = new String[hours.length+1][days.length];
    public final String ADDASSIGNMENT = "NEWASSIGNMENT", DELETEASSIGNMENT = "REMOVEASSIGNMENT", UPDATEASSIGNMENT = "UPDATEASSIGNMENT";
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem newAssignment = new JMenuItem("Crear una asignatura aqui"), deleteAssignment = new JMenuItem("Eliminar esta asignatura"), updateAssignment = new JMenuItem("Actualizar esta asignatura");

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    public SchedulePanel() {
        load();
        initialSetup();
        addComponents();
    }

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    private void load() {
        for (int i = 0; i < hours.length+1; i++) {
            for (int j = 0; j < days.length; j++) {
                schedule[i][j] = new JTextField();
                schedule[i][j].setHorizontalAlignment(JTextField.CENTER);
                schedule[i][j].setSelectionColor(new Color(0, 102, 204));
                schedule[i][j].setSelectedTextColor(new Color(255,255,255));
                HORARIO[i][j] = i + "," + j;
                schedule[i][j].setActionCommand(HORARIO[i][j]);
            }
        }
        setLayout(new GridLayout(hours.length+1, days.length));
        newAssignment.setActionCommand(ADDASSIGNMENT);
        updateAssignment.setActionCommand(UPDATEASSIGNMENT);
        deleteAssignment.setActionCommand(DELETEASSIGNMENT);
    }

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    private void initialSetup() {
        for (int i = 0; i < hours.length+1; i++) {
            schedule[i][0].setBackground(new Color(100, 100, 100));
            schedule[i][0].setForeground(new Color(255,255,255));
            schedule[i][0].setEnabled(false);
        }
        for (int i = 0; i < days.length; i++) {
            schedule[0][i].setBackground(new Color(100,100,100));
            schedule[0][i].setForeground(new Color(255,255,255));
            schedule[0][i].setText(days[i]);
            schedule[0][i].setEnabled(false);
        }
        for (int i = 1; i < hours.length+1; i++) {
            schedule[i][0].setText(hours[i-1]);
        }
    }

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    private void addComponents() {
        for (int i = 0; i < hours.length+1; i++) {
            for (int j = 0; j < days.length; j++) {
                add(schedule[i][j]);
            }
        }
    }

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    public void addOrUpdateAssignment(int hour, int day, String name, Color rgb) {
        var red = rgb.getRed();
        var green = rgb.getGreen();
        var blue = rgb.getBlue();
        if((red < 125 && green < 125 && blue < 125) ||
                (red < 125 && green < 125 && blue < 180) ||
                (red < 125 && blue < 125 && green < 180) ||
                (blue < 125 && green < 125 && red < 180)) {
            schedule[hour][day].setForeground(new Color(255,255,255));
        } else {
            schedule[hour][day].setForeground(new Color(0,0,0));
        }
        schedule[hour][day].setBackground(rgb);
        schedule[hour][day].setText(name);
    }

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    public void deleteAssignment(int hour, int day) {
        schedule[hour][day].setText("");
        schedule[hour][day].setBackground(new Color(255,255,255));
    }

    /**
     * Constructor method of the class. <br>
     * <i><b>Pre</b>condition</i>: <br>
     * <i><b>Post</b>condition</i>:
     * @author Gabriel Blanco
     */
    public void onSecondClick(int hour, int day) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.show(null, e.getX(), e.getY());
                if((schedule[hour][day].getText().equals("") || schedule[hour][day].getText().equals(null)) && schedule[hour][day].getBackground() == new Color(255,255,255) ) {
                    popupMenu.add(newAssignment);
                }
                if(!(schedule[hour][day].getText().equals("") || schedule[hour][day].getText().equals(null))) {
                    popupMenu.add(updateAssignment);
                    popupMenu.add(deleteAssignment);
                }
            }
        });
    }

    public JTextField[][] getSchedule() {
        return schedule;
    }

    public String[] getDays() {
        return days;
    }

    public String[] getHours() {
        return hours;
    }

}

