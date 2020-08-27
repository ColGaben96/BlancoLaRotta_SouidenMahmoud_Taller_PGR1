package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Constructor method of the class. <br>
 * @author Gabriel Blanco
 * @version 1.0
 */
public class GradesPanel extends JPanel {
    private JTable table = null;
    private DefaultTableModel model = null;
    private JScrollPane scroll = null;
    private JButton addPeriod = new JButton("Agregar Corte"), deletePeriod = new JButton("Eliminar Corte");
    private JLabel grade = new JLabel("Promedio: ");
    private JPanel tablePanel = new JPanel(),
            periodsPanel = new JPanel();
    private int counter = 0;
    public final String ADDPERIOD = "NEWPERIOD", DELETEPERIOD = "DELETEPERIOD";

    /**
     * Constructor method of the class. <br>
     * @author Gabriel Blanco
     */
    public GradesPanel() {
        load();
        setupPeriodsPanel();
        setupTablePanel();
        addComponents();
    }

    private void load() {
        TitledBorder title = new TitledBorder("Notas");
        setLayout(new GridLayout(2,1));
        setBorder(title);
        table = new JTable(new DefaultTableModel(new Object[] {"Corte #","Nota del Corte", "% del corte"}, counter));
        counter++;
        model = (DefaultTableModel) table.getModel();
        table.setAutoscrolls(true);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setViewportView(table);
        setBackground(new Color(255,255,255));
        addPeriod();
        deletePeriod.setEnabled(false);
    }

    private void setupTablePanel() {
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scroll, BorderLayout.CENTER);
        tablePanel.add(grade, BorderLayout.EAST);
        tablePanel.setBackground(new Color(255,255,255));
    }

    private void setupPeriodsPanel() {
        periodsPanel.setLayout(new GridLayout(1,2));
        addPeriod.setActionCommand(ADDPERIOD);
        deletePeriod.setActionCommand(DELETEPERIOD);
        periodsPanel.add(addPeriod);
        periodsPanel.add(deletePeriod);
        periodsPanel.setBackground(new Color(255,255,255));
    }

    private void addComponents() {
        add(periodsPanel);
        add(tablePanel);
    }

    public void addPeriod() {
        model.addRow(new Object[]{counter,"",""});
        counter++;
        deletePeriod.setEnabled(true);
    }

    public void removePeriod() {
        model.removeRow(counter-2);
        counter--;
        if(counter < 3) {
            deletePeriod.setEnabled(false);
        }
    }

    public int getPeriodCount() {
        return table.getRowCount();
    }

    public double[] getGrades() {
        var tableGrades = "";
        for (int i = 0; i < table.getRowCount(); i++) {
            tableGrades += table.getValueAt(i,1)+",";
        }
        var grades = tableGrades.split(",");
        double[] doubleGrades = new double[grades.length];
        for (int i = 0; i < grades.length; i++) {
            doubleGrades[i] = Double.parseDouble(grades[i]);
        }
        return doubleGrades;
    }

    public JButton getAddPeriod() {
        return addPeriod;
    }

    public JButton getDeletePeriod() {
        return deletePeriod;
    }
}
