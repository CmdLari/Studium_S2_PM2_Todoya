package todoyaf.todoyaf;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.*;

public class ListManager extends BaseController {

    @FXML
    public Button add;
    @FXML
    public TextField enterTask;
    @FXML
    public ListView<Task> taskView;
    @FXML
    public ChoiceBox<String> completeCancel;
    @FXML
    public Button safe;
    @FXML
    public Button load;

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

    public void saveList() {
        try (FileWriter fw = new FileWriter("data.txt")) {
            try (BufferedWriter writer = new BufferedWriter(fw)) {
                for (Task task : taskView.getItems()) {
                    writer.write("task:" + task.name + "&&&" + task.note.replace("\n", " "));
                    writer.newLine();
                }
                writer.write("streak:" + model.winStreak.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadList(){
        model.taskList.clear();
        model.winStreak.set(0);

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String newLine;
            while ((newLine = reader.readLine())!=null){
                if(newLine.startsWith("task:")){
                    String[] newLineAry = newLine.split("&&&");
                    Task newTask = new Task(newLineAry[0].substring(5), newLineAry[1]);
                    model.taskList.add(newTask);
                        }
                else if (newLine.startsWith("streak:")){
                    model.winStreak.set(Integer.parseInt(newLine.substring(7)));
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
