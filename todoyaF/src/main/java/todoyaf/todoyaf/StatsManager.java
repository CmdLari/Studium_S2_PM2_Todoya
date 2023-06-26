package todoyaf.todoyaf;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StatsManager extends BaseController {

    @FXML
    Text tasksShow;

    @FXML
    Text winStreak;

    @Override
    public void configureModel(Model model) {
        model.taskList.addListener(this::updateEnteredTasks);
        model.winStreak.addListener(this::updateWinStreak);
        super.configureModel(model);
    }

    private void updateEnteredTasks(ListChangeListener.Change<? extends Task> change) {
        tasksShow.setText(Integer.toString(change.getList().size()));
    }

    private void updateWinStreak(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        winStreak.setText(newValue.toString());
    }

}
