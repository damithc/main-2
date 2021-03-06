//@@author A0112204E
package ui.Controllers;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import ui.Controllers.MainGUIController;
import logic.Task;
import shared.LogHandler;

public class ListInterfaceController {
	
	/**
	 * 	ListInterfaceController controls the to-do list
	 * 	Primarily functions to displays the user's task list by manipulating how tasks 
	 *  a set into the list view
	 * 	It also filters the list and generate notifications by calling other classes
	 * 
	 */

	private static final int TASK_LIST_ANIMTATION_DELAY = 200;
	private static final int TASK_LIST_ANIMATION_DURATION = 400;
	private static final int REMINDER_NOTIFICATION_INTERVAL = 60000;
	private static final int GLYPH_SIZE = 20;
	private static final int TASK_START_TIME_WIDTH = 55;
	private static final int TASK_END_TIME_WIDTH = 80;
	private static final int TASK_START_DATE_WIDTH = 55;
	private static final int TASK_END_DATE_WIDTH = 55;
	private static final int TASK_NAME_WIDTH = 240;
	private static final int TASK_INDEX_WIDTH = 40;
	private static final int TASKROW_SPACING = 10;
	private static final int REMINDER_TIME = 15;
	private static final int TIME_FLAG = 1;
	private static final int DAY_FLAG = 0;
	private static final int INIT_SCROLL = 0;

	private static final String COMPLETED_TAB = "  Completed";
	private static final String PENDING_TAB = "  To-Dos";
	private static final String TODAY_TAB = "  Today";
	private static final String TOMORROW_TAB = "  Tomorrow";
	private static final String FLOAT_TAB = "  Tentative";
	private static final String OTHERS_TAB = "  Upcoming";
	private static final String OVERDUE_TAB = "  Overdue";

	private static final String INDEX_ID = "taskIndex";
	private static final String TASK_ID = "taskName";
	private static final String DATE_ID = "taskDate";
	private static final String TIME_ID = "taskTime";
	private static final String COMPLETED_TASK_ID = "completedTaskName";

	private static final String PENDING_TAB_ID = "tabPending";
	private static final String COMPLETED_TAB_ID = "tabCompleted";
	private static final String TODAY_TAB_ID = "tabToday";
	private static final String TOMORROW_TAB_ID = "tabTomorrow";
	private static final String OTHERS_TAB_ID = "tabOthers";
	private static final String FLOAT_TAB_ID = "tabFloat";
	private static final String OVERDUE_TAB_ID = "tabOverdue";

	private static final String EMPTY_LIST_FEEDBACK = "Your task list is empty.";
	private static final String EMPTY_LIST_MESSAGE = "Take a break and enjoy your day! You deserve it!";
	private static final String EMPTY_LIST_ID = "emptyRow";
	private static final String END_DATETIME_PLACEHOLDER = "to ";
	private static final String NULL = "";

	private static final String DATE_FORMAT = "dd/MM/yy";
	private static final String TIME_FORMAT = "HHmm";

	private static final String TASK_INDEX_FORMAT = "  %1s.";

	private static final boolean OPEN_LIST = true;
	private static final boolean CLOSE_LIST = false;
	private Logger log = LogHandler.retrieveLog();

	private MainGUIController main;

	@FXML private ListView<HBox> todoList;
	@FXML private HBox todoListContainer;
	@FXML private TabPane tabPane;
	@FXML private Tab tabPending = new Tab(PENDING_TAB);

	private Tab tabComplete = new Tab(COMPLETED_TAB);
	private Tab tabToday = new Tab(TODAY_TAB);
	private Tab tabTomorrow = new Tab(TOMORROW_TAB);
	private Tab tabOthers = new Tab(OTHERS_TAB);
	private Tab tabFloat = new Tab(FLOAT_TAB);
	private Tab tabOverdue = new Tab(OVERDUE_TAB);

	private static ObservableList<HBox> pendingTasks = FXCollections.observableArrayList();
	private static ObservableList<HBox> completedTasks = FXCollections.observableArrayList();
	private static ArrayList<Tab> tabs = new ArrayList<Tab>();

	private ArrayList<Task> operatingTasksFromLogic = new ArrayList<Task>();
	private ArrayList<Task> completedTasksFromLogic = new ArrayList<Task>();

	private static ObservableList<HBox> todayTasks =
			FXCollections.observableArrayList();
	private static ObservableList<HBox> tomorrowTasks =
			FXCollections.observableArrayList();
	private static ObservableList<HBox> otherTasks =
			FXCollections.observableArrayList();
	private static ObservableList<HBox> floatingTasks =
			FXCollections.observableArrayList();
	private static ObservableList<HBox> overdueTasks =
			FXCollections.observableArrayList();

	private int previousTasksSize;

	private HBox scrollSelection = new HBox();

	private TaskFilter taskFilter = new TaskFilter();
	private NotificationRenderer notification = new NotificationRenderer();
	
	/**
	 * Constructor method
	 * @param mainController The primary controller linking this and the other controllers
	 */
	public void init(MainGUIController mainController) {
		main = mainController;

		initTabPane();
		loopTaskList();
		hideToDoList();
		loopCheckTasksForReminder();
		setTaskIntoViewIndex(INIT_SCROLL);
	}

	private void initTabPane() {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		initTabPaneListener();
	}

	private void initTabPaneListener() {
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				log.info("Tab focused: " + newValue);
				if (newValue.getContent() == null) {
					if(newValue.equals(tabPending)) {
						displayPending();
					} else if (newValue.equals(tabOverdue)) {
						displayOverdue();
					} else if (newValue.equals(tabToday)) {
						displayToday();
					} else if (newValue.equals(tabTomorrow)) {
						displayTomorrow();
					} else if (newValue.equals(tabOthers)) {
						displayOthers();
					} else if (newValue.equals(tabFloat)) {
						displayFloat();
					} else if (newValue.equals(tabComplete)) {
						displayComplete();
					}
					newValue.setContent(todoList);
					oldValue.setContent(null);
				}   
			}
		});
	}
	
	/**
	 * Initializes and maintain each tab filter
	 */
	private void operateTabs() {
		tabs.clear();
		pendingTabHandler();
		overdueTabHandler();
		todayTabHandler();
		tomorrowTabHandler();
		othersTabHandler();
		floatTabHandler();
		completedTabHandler();
		tabPane.getTabs().setAll(tabs);
	}

	private void floatTabHandler() {
		if(!tabs.contains(tabFloat) && !floatingTasks.isEmpty()) {
			tabFloat = new Tab(floatingTasks.size() + FLOAT_TAB);
			tabFloat.setId(FLOAT_TAB_ID);
			tabs.add(tabFloat);
		} else if(tabs.contains(tabFloat) && floatingTasks.isEmpty()) {
			tabs.remove(tabFloat);
		}
	}

	private void othersTabHandler() {
		if(!tabs.contains(tabOthers) && !otherTasks.isEmpty()) {
			tabOthers = new Tab(otherTasks.size() + OTHERS_TAB);
			tabOthers.setId(OTHERS_TAB_ID);
			tabs.add(tabOthers);
		} else if(tabs.contains(tabOthers) && otherTasks.isEmpty()) {
			tabs.remove(tabOthers);
		}
	}

	private void tomorrowTabHandler() {
		if(!tabs.contains(tabTomorrow) && !tomorrowTasks.isEmpty()) {
			tabTomorrow = new Tab(tomorrowTasks.size() + TOMORROW_TAB);
			tabTomorrow.setId(TOMORROW_TAB_ID);
			tabs.add(tabTomorrow);
		} else if(tabs.contains(tabTomorrow) && tomorrowTasks.isEmpty()) {
			tabs.remove(tabTomorrow);
		}
	}

	private void todayTabHandler() {
		if(!tabs.contains(tabToday) && !todayTasks.isEmpty()) {
			tabToday = new Tab(todayTasks.size() + TODAY_TAB);
			tabToday.setId(TODAY_TAB_ID);
			tabs.add(tabToday);
		} else if(tabs.contains(tabToday) && todayTasks.isEmpty()) {
			tabs.remove(tabToday);
		}
	}

	private void overdueTabHandler() {
		if(!tabs.contains(tabOverdue) && !overdueTasks.isEmpty()) {
			tabOverdue = new Tab(overdueTasks.size() + OVERDUE_TAB);
			tabOverdue.setId(OVERDUE_TAB_ID);
			tabs.add(tabOverdue);
		} else if(tabs.contains(tabOverdue) && overdueTasks.isEmpty()) {
			tabs.remove(tabOverdue);
		}
	}

	private void completedTabHandler() {
		if(!tabs.contains(tabComplete) && !completedTasksFromLogic.isEmpty()) {
			tabComplete =  new Tab(completedTasksFromLogic.size() + COMPLETED_TAB);
			tabComplete.setId(COMPLETED_TAB_ID);
			tabs.add(tabComplete);
		} else if(tabs.contains(tabComplete) && completedTasksFromLogic.isEmpty()){
			tabs.remove(tabComplete);
		}
	}

	private void pendingTabHandler() {
		tabPending =  new Tab(operatingTasksFromLogic.size() + PENDING_TAB);
		tabPending.setId(PENDING_TAB_ID);
		tabs.add(tabPending);
	}

	/**
	 * Refreshes task list and display the new task list if there are any changes
	 */
	public void loopTaskList() {
		pendingTasks.clear();
		completedTasks.clear();

		setOperatingTasksFromLogic();
		setCompletedTasksFromLogic();
		openToDoList();
		displayTasksonToDoList();
	}

	private void displayTasksonToDoList() {
		formatPendingTaskToListCell(operatingTasksFromLogic);
		formatCompletedTaskToListCell(completedTasksFromLogic);
		setTaskIntoViewObject(scrollSelection);
		operateTabs();
	}

	/**
	 * Sets tasks into view and maintains the properties for the display of each incomplete task
	 * @param taskList This is the list of task which is maintained in the storage
	 */
	private void formatPendingTaskToListCell(ArrayList<Task> taskList) {
		log.info("Pending task List is reformatted");
		
		int index = 0;
		for(Task taskObj: taskList) {
			index++;
			HBox taskRow = createTaskRow();
			Label taskIndex = createTaskIndex(index);
			Label taskName = createTaskName(taskObj, taskList);
			Label taskStartTime = createTaskStartTime(taskObj);
			Label taskEndTime = createTaskEndTime(taskObj);
			Label taskStartDate = createTaskStartDate(taskObj); 
			Label taskEndDate = createTaskEndDate(taskObj);
			setProperties(taskIndex, taskName, taskStartDate, taskEndDate, taskStartTime, taskEndTime, taskRow);
			
			if(todoListContainer.getScaleX() == 0) {
				animateToDoList(OPEN_LIST);
			}
			
			if(index == taskList.size() && taskList.size() != previousTasksSize) {
				scrollSelection = taskRow;
				previousTasksSize = operatingTasksFromLogic.size();
			} 

			if(taskFilter.isEventClashing(taskObj, taskList)) {
				Glyph glyph = new FontAwesome().create(FontAwesome.Glyph.EXCLAMATION_CIRCLE).size(GLYPH_SIZE);
				taskRow.getChildren().addAll(taskIndex, glyph, taskName, taskStartTime, taskStartDate, taskEndTime, taskEndDate);
				FeedbackGenerator.generateEventClashFeedback(main.getHelpFeedback());
			} else {
				taskRow.getChildren().addAll(taskIndex, taskName, taskStartTime, taskStartDate, taskEndTime, taskEndDate);
			}
			taskFilter.sortTasksByClasses(taskObj, taskRow);
		}	
		setTasksForCategories();
		taskFilter.addSortedClasses(pendingTasks);
		setFeedbackForEmptyList(taskList);
		todoList.setItems(pendingTasks);
	}
	
	/**
	 * Sets tasks into view and maintains the properties for the display of each completed task
	 * @param taskList This is the list of task which is maintained in the storage
	 */
	private void formatCompletedTaskToListCell(ArrayList<Task> taskList) {
		log.info("Completed task List is reformatted");
		
		int index = 0;
		for(Task taskObj: taskList) {
			index++;
			HBox taskRow = createTaskRow();
			Label taskIndex = createTaskIndex(index);
			Label taskName = createTaskName(taskObj, taskList);
			Label taskStartTime = createTaskStartTime(taskObj);
			Label taskEndTime = createTaskEndTime(taskObj);
			Label taskStartDate = createTaskStartDate(taskObj); 
			Label taskEndDate = createTaskEndDate(taskObj); 
			
			setProperties(taskIndex, taskName, taskStartDate, taskEndDate, taskStartTime, taskEndTime, taskRow);
			taskName.setId(COMPLETED_TASK_ID);
			taskRow.getChildren().addAll(taskIndex, taskName, taskStartTime, taskEndTime, taskStartDate, taskEndDate);
			completedTasks.add(taskRow);
		}
	}

	private Label createTaskStartDate(Task taskObj) {
		Label taskDate = new Label();
		if(!taskObj.get_startDate().isEmpty()) {
			taskDate = new Label(taskObj.get_startDate());
		}
		return taskDate;
	}

	private Label createTaskEndDate(Task taskObj) {
		Label taskDate = new Label();
		if(!taskObj.get_endDate().isEmpty()) {
			taskDate = new Label(taskObj.get_endDate());
		}
		return taskDate;
	}

	private Label createTaskStartTime(Task taskObj) {
		return new Label(taskObj.get_startTime());
	}

	private Label createTaskEndTime(Task taskObj) {
		Label taskTime = new Label();
		if(!taskObj.get_endTime().isEmpty()){
			taskTime = new Label(END_DATETIME_PLACEHOLDER + taskObj.get_endTime());
		}
		return taskTime;
	}

	private Label createTaskName(Task taskObj, ArrayList<Task> taskList) {
		return new Label(taskObj.get_task());
	}

	private Label createTaskIndex(int index) {
		return new Label(String.format(TASK_INDEX_FORMAT, index));
	}

	private HBox createTaskRow() {
		return new HBox(TASKROW_SPACING);
	}

	/**
	 * Loads to-do list into view
	 */
	public void openToDoList() {
		if(pendingTasks.isEmpty() && completedTasks.isEmpty() && main.getMainPane().isManaged()) {	
			todoListContainer.setManaged(true);
			todoListContainer.setOpacity(1);

			main.removeMainPane();
			animateToDoList(OPEN_LIST);
		}
	}
	
	/**
	 * Remove to-do list from view
	 */
	public void closeToDoList() {
		if(todoListContainer.getScaleX() == 1) {
			animateToDoList(CLOSE_LIST);
		}
	}

	private void hideToDoList() {
		if(pendingTasks.size() <= 1) {
			todoListContainer.setManaged(false);
			todoListContainer.setOpacity(0);
			closeToDoList();
		}
	}

	private void animateToDoList(boolean isOpen) {
		if(isOpen) {
			ScaleTransition st = new ScaleTransition(Duration.millis(TASK_LIST_ANIMATION_DURATION), todoListContainer);
			st.setFromX(0);
			st.setToX(1);
			st.setCycleCount(1);
			st.setDelay(Duration.millis(TASK_LIST_ANIMTATION_DELAY));
			st.play();
			todoListContainer.setManaged(true);
		} else {
			ScaleTransition st = new ScaleTransition(Duration.millis(TASK_LIST_ANIMATION_DURATION), todoListContainer);
			st.setFromX(1);
			st.setToX(0);
			st.setCycleCount(1);
			st.setDelay(Duration.millis(TASK_LIST_ANIMTATION_DELAY));
			st.play();
			todoListContainer.setManaged(false);
		}
	}
	
	/**
	 * Loops and check if there is any upcoming tasks every minute
	 */
	private void loopCheckTasksForReminder() {
		Timer checkTasks = new Timer(true);
		checkTasks.schedule(new TimerTask() {
			@Override
			public void run() {  
				Platform.runLater(new Runnable() {
					public void run() {
						checkTasksForReminder();
					}
				});

			}
		}, 0, REMINDER_NOTIFICATION_INTERVAL);
	}

	private void checkTasksForReminder() {
		if(!operatingTasksFromLogic.isEmpty()) {
			int todoTime = 0;
			int todoDay = 0;
			LocalDateTime localDateTime = new LocalDateTime();
			LocalDate localDate = localDateTime.toLocalDate();
			LocalTime localTime = localDateTime.toLocalTime().plusMinutes(REMINDER_TIME);
			
			for(Task taskObj: operatingTasksFromLogic) {
				if(taskObj.get_startDate().equals(localDate.toString(DATE_FORMAT)) ||
						(taskObj.get_startDate().equals(NULL) && !taskObj.get_startTime().equals(NULL))) {
					if(taskObj.get_startTime().equals(localTime.toString(TIME_FORMAT))) {
						todoTime++;
					}
					todoDay++;
				}
			}

			if(todoTime > 0) {
				log.info("Notification rendered");
				notification.loadNotification(todoTime, TIME_FLAG);
			} else if(todoDay > 0 && todoTime == 0) {
				log.info("Notification rendered");
				notification.loadNotification(todoDay, DAY_FLAG);
			}
		}
	}

	private void setCompletedTasksFromLogic() {
		completedTasksFromLogic = main.getCompletedTasksFromLogic();
	}

	private void setOperatingTasksFromLogic() {
		operatingTasksFromLogic = main.getIncompleteTasksFromLogic();
	}

	private void setTasksForCategories() {
		taskFilter.getTasksToday(todayTasks);
		taskFilter.getTasksTomorrow(tomorrowTasks);
		taskFilter.getTasksOthers(otherTasks);
		taskFilter.getTasksFloat(floatingTasks);
		taskFilter.getTasksOverdue(overdueTasks);

	}

	private void setTaskIntoViewIndex(int index) {
		if(pendingTasks.size() > 1) {
			todoList.scrollTo(index);
		}
	}

	private void setTaskIntoViewObject(HBox obj) {
		if(pendingTasks.contains(obj)) {
			todoList.scrollTo(obj);
		}
	}

	private void setFeedbackForEmptyList(ArrayList<Task> taskList) {
		if(taskList.isEmpty() ) {
			HBox emptyRow = new HBox();
			VBox rowContainer = new VBox(10);
			Label feedback = new Label(EMPTY_LIST_FEEDBACK);
			Label message = new Label(EMPTY_LIST_MESSAGE);

			emptyRow.setId(EMPTY_LIST_ID);
			rowContainer.setId(EMPTY_LIST_ID);
			rowContainer.getChildren().addAll(feedback, message);
			emptyRow.getChildren().add(rowContainer);
			pendingTasks.add(emptyRow);
		}
	}

	private void setProperties(Label index, Label name, Label date1, Label date2, Label time1, Label time2, HBox task) {
		task.setPrefWidth(600);

		HBox.setHgrow(name, Priority.ALWAYS);
		index.setPrefWidth(TASK_INDEX_WIDTH);
		index.setMaxWidth(Double.MAX_VALUE);
		index.setId(INDEX_ID);

		HBox.setHgrow(name, Priority.ALWAYS);
		name.setPrefWidth(TASK_NAME_WIDTH);
		name.setMaxWidth(Double.MAX_VALUE);
		name.setId(TASK_ID);
		name.setWrapText(true);

		HBox.setHgrow(date1, Priority.ALWAYS);
		date1.setPrefWidth(TASK_START_DATE_WIDTH);
		date1.setId(DATE_ID);

		HBox.setHgrow(date2, Priority.ALWAYS);
		date2.setPrefWidth(TASK_END_DATE_WIDTH);
		date2.setId(DATE_ID);

		HBox.setHgrow(time1, Priority.ALWAYS);
		time1.setPrefWidth(TASK_START_TIME_WIDTH);
		time1.setId(TIME_ID);

		HBox.setHgrow(time2, Priority.ALWAYS);
		time2.setPrefWidth(TASK_END_TIME_WIDTH);
		time2.setId(TIME_ID);
	}

	/**
	 * Gets tab pane 
	 * @return TabPane The tab pane containing filter tabs
	 */
	public TabPane getTabPane() {
		return tabPane;
	}

	private void displayPending() {
		main.setOperatingListAsIncomplete();
		todoList.setItems(pendingTasks);
	}

	private void displayComplete() {
		main.setOperatingListAsComplete();
		todoList.setItems(completedTasks);
	}

	private void displayToday() {
		main.setOperatingListAsIncomplete();
		todoList.setItems(todayTasks);
	}

	private void displayTomorrow() {
		main.setOperatingListAsIncomplete();
		todoList.setItems(tomorrowTasks);
	}

	private void displayOverdue() {
		main.setOperatingListAsIncomplete();
		todoList.setItems(overdueTasks);
	}

	private void displayOthers() {
		main.setOperatingListAsIncomplete();
		todoList.setItems(otherTasks);
	}

	private void displayFloat() {
		main.setOperatingListAsIncomplete();
		todoList.setItems(floatingTasks);
	}

	/**
	 * Gets the list of incomplete tasks
	 * @return ObservableList<HBox> List of incomplete task in HBox
	 */
	public ObservableList<HBox> getTasks() {
		return pendingTasks;
	}

	/**
	 * Gets the list of completed tasks
	 * @return ObservableList<HBox> List of completed Tasks in HBox
	 */
	public ObservableList<HBox> getCompleted() {
		return completedTasks;
	}
	
	/**
	 * Gets the entire list pane from todoListController
	 * @return ListView<HBox> The list view pane
	 */
	public ListView<HBox> getList() {
		return todoList;
	}
}