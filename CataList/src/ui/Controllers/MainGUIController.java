//@@author A0112204E
package ui.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.LogicMain;
import logic.Task;

public class MainGUIController {

	@FXML public CommandLineController commandLineController;  
	@FXML public ListInterfaceController todoListController;   
	@FXML public TitleInterfaceController titleController;
	@FXML public SupportFeaturesController supportFeaturesController;
	@FXML public AnchorPane backgroundPane;

	private LogicMain logic = new LogicMain();

	public void initialize() throws IOException, JDOMException {
		commandLineController.init(this);
		todoListController.init(this);
		titleController.init(this);
		supportFeaturesController.init(this);
	}
	
	public void refreshToDoList() throws IOException, JDOMException {
		todoListController.loopTaskList();
	}

	public void removeMainPane() {
		supportFeaturesController.removeMainPane();
	}

	public void openMainPane() {
		supportFeaturesController.showMainPane();
	}
	
	public String passInputToLogic(String input) {
		return logic.processCommand(input);
	}
	
	public AnchorPane getBackgroundPane() {
		return backgroundPane;
	}
	
	public ObservableList<HBox> getTaskList() {
		return todoListController.getTasks();
	}

	public ObservableList<HBox> getCompletedList() {
		return todoListController.getCompleted();
	}

	public VBox getMainPane() {
		return supportFeaturesController.getMainPane();
	}

	public LogicMain getLogic() {
		return logic;
	}

	public ArrayList<Task> getOperatingTasksFromLogic() {
		logic.operatingToIncomplete();
		return logic.getOperatingTasksForUI();
	}
	
	public ArrayList<Task> getCompletedTasksFromLogic() {
		logic.operatingToComplete();
		return logic.getCompleteTasksForUI();
	}
	
	public ArrayList<Task> getPendingTasksFromLogic() {
		logic.operatingToIncomplete();
		return logic.getIncompleteTasksForUI();
	}

	public TabPane getTabPane() {
		return todoListController.getTabPane();
	}
	
	public TextField getCommandLine() {
		return commandLineController.getCommandLine();
	}
	
	public ListView<HBox> getList() {
		return todoListController.getList();
	}
	
	public String getTutorialMode() {
		return commandLineController.getTutorialToggle();
	}

}