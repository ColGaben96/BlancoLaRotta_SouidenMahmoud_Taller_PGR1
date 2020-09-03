package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemListener;

public class ProfileFrame extends JFrame {
    private JPanel salutePanel = new JPanel(),
    personalInfoPanel = new JPanel(),
    academicInfoPanel = new JPanel(),
    operationsPanel = new JPanel(),
    aioPanel = new JPanel();
    private JLabel salutations = new JLabel("A continuacion te vamos a realizar unas preguntas para que puedas usar el programa."),
    lbName = new JLabel("¿Como te llamas?"),
    lbAverage = new JLabel("¿Cual es tu promedio?"),
    lbSemester = new JLabel("¿En que semestre vas?"),
    lbStatus = new JLabel("¿En que condicion académica te encuentras?"),
    lbPlan = new JLabel("¿Estudias de dia o de noche?"),
    lbScholarship = new JLabel("¿Tienes alguna beca?"),
    lbCareer = new JLabel("¿Que carrera estudias?");
    private JTextField txName = new JTextField(),
    txSemester = new JTextField(),
    txAverage = new JTextField(),
    txCareer = new JTextField();
    private String[] arrStatusCommon = new String[]{"Normal","Extracreditado","Prueba Académica: Nivel I","Prueba Académica: Nivel II"},
            arrStatusSchool = new String[]{"Normal","En Riesgo"},
            arrPlan = new String[]{"Diurno","Nocturno"},
            arrScholarship = new String[]{"Si","No"};
    private JComboBox<String> comboStatus = new JComboBox<String>(arrStatusCommon),
    comboPlan = new JComboBox<String>(arrPlan),
    comboScholarship = new JComboBox<String>(arrScholarship);
    DefaultComboBoxModel commonModel = new DefaultComboBoxModel(arrStatusCommon),
        schoolModel = new DefaultComboBoxModel(arrStatusSchool);
    private JButton finishOOBE = new JButton("Acepto los términos y condiciones y comenzar a usar el programa");
    public final String FINISHOOBE = "FINISHEDOOBECREATENOW";


    public ProfileFrame() {
        load();
        addComponents();
    }

    private void load() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Mi Horario");
        setSize(550,500);
        setLayout(new BorderLayout());
        loadSP();
        loadPIP();
        loadAIP();
        loadOIP();
        setupPanels();
        finishOOBE.setActionCommand(FINISHOOBE);
        comboScholarship.setSelectedIndex(1);
        ItemListener itemListener = e -> {
            if(comboScholarship.getSelectedIndex() == 0) {
                comboPlan.setSelectedIndex(0);
                comboPlan.setEnabled(false);
                comboStatus.setModel(schoolModel);
            } else {
                comboPlan.setSelectedIndex(0);
                comboPlan.setEnabled(true);
                comboStatus.setModel(commonModel);
            }
        };
        comboScholarship.addItemListener(itemListener);
        setLocationRelativeTo(null);
    }

    private void loadSP() {
        TitledBorder title = new TitledBorder("¡Hola! Bienvenido/a a Mi Horario");
        salutePanel.setBorder(title);
        salutePanel.setLayout(new BorderLayout());
        salutePanel.add(salutations, BorderLayout.CENTER);
    }

    private void loadPIP() {
        TitledBorder title = new TitledBorder("Tu información personal");
        personalInfoPanel.setBorder(title);
        personalInfoPanel.setLayout(new GridLayout(4,2));
        personalInfoPanel.add(lbName); personalInfoPanel.add(txName);
        personalInfoPanel.add(lbCareer); personalInfoPanel.add(txCareer);
        personalInfoPanel.add(lbSemester); personalInfoPanel.add(txSemester);
        personalInfoPanel.add(lbAverage); personalInfoPanel.add(txAverage);
    }

    private void loadAIP() {
        TitledBorder title = new TitledBorder("Tu información académica");
        academicInfoPanel.setBorder(title);
        academicInfoPanel.setLayout(new GridLayout(3,2));
        academicInfoPanel.add(lbScholarship); academicInfoPanel.add(comboScholarship);
        academicInfoPanel.add(lbStatus); academicInfoPanel.add(comboStatus);
        academicInfoPanel.add(lbPlan); academicInfoPanel.add(comboPlan);
    }

    private void loadOIP() {
        operationsPanel.setLayout(new BorderLayout());
        operationsPanel.add(finishOOBE, BorderLayout.CENTER);

    }

    private void setupPanels() {
        aioPanel.setLayout(new GridLayout(2,1));
        aioPanel.add(personalInfoPanel);
        aioPanel.add(academicInfoPanel);
    }

    private void addComponents() {
        add(salutePanel, BorderLayout.NORTH);
        add(aioPanel, BorderLayout.CENTER);
        add(operationsPanel, BorderLayout.SOUTH);
    }

    public JTextField getTxName() {
        return txName;
    }

    public JTextField getTxSemester() {
        return txSemester;
    }

    public JTextField getTxAverage() {
        return txAverage;
    }

    public JComboBox<String> getComboStatus() {
        return comboStatus;
    }

    public JComboBox<String> getComboPlan() {
        return comboPlan;
    }

    public JComboBox<String> getComboScholarship() {
        return comboScholarship;
    }

    public JButton getFinishOOBE() {
        return finishOOBE;
    }

    public JTextField getTxCareer() {
        return txCareer;
    }
}
