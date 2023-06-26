package todoyaf.todoyaf;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ListManager extends BaseController {

    @FXML
    public Button add;
    @FXML
    public TextField enterTask;
    @FXML
    public ListView<Task> taskView;
    @FXML
    public ChoiceBox<String> completeCancel;

    private final ObservableList<String> choices = FXCollections.observableArrayList("complete", "cancel");

    @Override
    public void configureModel(Model model) {
        completeCancel.setItems(choices);
        model.taskList.addListener(this::updateListView);
        taskView.getSelectionModel().getSelectedItems().addListener(this::updateSelectedTask);
        super.configureModel(model);
        completeCancelviaContext();
    }

    private void updateSelectedTask(ListChangeListener.Change<? extends Task> change) {
        if(!(change.getList().size()==0)){
            model.selectedTask.setValue(change.getList().get(0));
        }
    }

    public void addTask() {
        String taskName = enterTask.getText().trim();
        if (!taskName.isEmpty()) {
            model.addTask(new Task(taskName));
            enterTask.clear();
        }
    }

    @FXML
    private void completeCancel() {
        Task selectedTask = taskView.getSelectionModel().getSelectedItem();
        String selectedAction = completeCancel.getSelectionModel().getSelectedItem();

        if (selectedTask != null && selectedAction != null) {
            if (selectedAction.equals("complete")) {
                completeMethod();
            } else if (selectedAction.equals("cancel")) {
                cancelMethod();
            }

            completeCancel.valueProperty().set(null);
        }
    }
    private void cancelMethod() {
        model.taskList.remove(taskView.getSelectionModel().getSelectedItem());
        model.winStreak.set(0);
    }

    private void completeMethod() {
        model.taskList.remove(taskView.getSelectionModel().getSelectedItem());
        model.winStreak.set(model.winStreak.getValue()+1);
    }

    private void completeCancelviaContext(){
        final ContextMenu completeCancelCon = new ContextMenu();
        MenuItem completeCon = new MenuItem();
        MenuItem cancelCon = new MenuItem();
        completeCon.setText("COMPLETE");
        cancelCon.setText("CANCEL");

        completeCancelCon.getItems().addAll(completeCon, cancelCon);
        taskView.setContextMenu(completeCancelCon);

        completeCon.setOnAction(actionEvent -> completeMethod());
        cancelCon.setOnAction(actionEvent -> cancelMethod());
    }

    public void updateListView(ListChangeListener.Change<? extends Task> change) {
        while (change.next()){
            for (Task removedTask : change.getRemoved()) {
                taskView.getItems().remove(removedTask);
            }
            for (Task addedTask : change.getAddedSubList()) {
                taskView.getItems().add(addedTask);
            }
        }
    }
}
