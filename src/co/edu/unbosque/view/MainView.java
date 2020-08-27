package co.edu.unbosque.view;
import co.edu.unbosque.controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * This is the principal and only one <b>Frame</b>. According to UEB's architecture, this is the communication with Controller.
 * @version 1.0 <br>
 * @author Gabriel Blanco <br>
 */
public class MainView extends JFrame {

	private JPanel viewComponents = null;
	private SchedulePanel schedule = new SchedulePanel();
	private PresentationPicture pic = new PresentationPicture();
	private CRUDAssignmentDialog crud = new CRUDAssignmentDialog();
	
	/**
	 * This is the method where initializes the whole view package. Won't do it in a constructor method because calling it from Controller will have trouble to load it later. <br>
	 * <i><b>Pre</b>condition:</i> Controller should give the instruction to paint the view with it's panels and listen the action components.<br>
	 * <i><b>Post</b>condition:</i> MainView received the order from Controller to paint the view and got prepared for listen the action components.<br>
	 * @version 1.0
	 * @author Gabriel Blanco <br>
	 */
	public void start(Controller controller) {
		load();
		TableNImgPresentation();
		listen(controller);
		addComponents();
	}
	
	/**
	 * This is the method where configure MainView. <br>
	 * <i><b>Pre</b>condition:</i> MainView's configuration details. This method should be launched by start() <br>
	 * <i><b>Post</b>condition:</i> MainView should have configured the parameters specified on it. <br>
	 * @version 1.0
	 * @author Gabriel Blanco
	 */
	private void load() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1366,768);
		setTitle("Horario: Gabriel Ernesto Blanco La Rotta & Rabih Nabyi Souiden Mahmoud");
		setLayout(new BorderLayout());
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * This is the method where the components inside this package are listened and executed by start() <br>
	 * <i><b>Pre</b>condition:</i> Controller should give the instruction to paint the view with it's panels and listen the action components. <br>
	 * <i><b>Post</b>condition:</i> MainView received the order from Controller to paint the view and got prepared for listen the action components. <br>
	 * @version 1.0
	 * @author Gabriel Blanco
	 */
	private void listen(Controller controller) {
		/* SCHEDULE */
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				schedule.getSchedule()[i][j].addActionListener(controller);
			}
		}
		/* CRUD */
		crud.getOk().addActionListener(controller);
		crud.getCancel().addActionListener(controller);
		crud.getRemoveAssignment().addActionListener(controller);
		crud.getColorPicker().addActionListener(controller);
		crud.getGradesPanel().getAddPeriod().addActionListener(controller);
		crud.getGradesPanel().getDeletePeriod().addActionListener(controller);
	}

	private void TableNImgPresentation() {
		viewComponents = new JPanel();
		viewComponents.setLayout(new GridLayout(2,1));
		viewComponents.add(pic);
		viewComponents.add(schedule);

	}
	
	/**
	 * This is the method where other panels with content are added to MainView to paint the whole package. This is executed by start() <br>
	 * <i><b>Pre</b>condition:</i> Controller should give the instruction to paint the view with it's panels and listen the action components. <br>
	 * <i><b>Post</b>condition:</i> MainView received the order from Controller to paint the view and got prepared for listen the action components. <br>
	 * @version 1.0
	 * @author Gabriel Blanco
	 */
	private void addComponents() {
		add(viewComponents, BorderLayout.CENTER);
	}

	public void onSecondClick() {

	}

	public SchedulePanel getSchedule() {
		return schedule;
	}

	public PresentationPicture getPic() {
		return pic;
	}

	public CRUDAssignmentDialog getCrud() {
		return crud;
	}
}
