package todoyaf.todoyaf;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Model {

    //Basis-Informationen
    @FXML
    public ObservableList<Task> taskList = FXCollections.observableArrayList();
    public IntegerProperty winStreak = new SimpleIntegerProperty(0);
    public Property<Task> selectedTask = new SimpleObjectProperty<>();

    public void addTask(Task newTask){
        taskList.add(newTask);
    }

}
