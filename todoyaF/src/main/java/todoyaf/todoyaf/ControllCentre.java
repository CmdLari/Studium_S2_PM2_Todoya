package todoyaf.todoyaf;

import javafx.fxml.FXML;

public class ControllCentre {

    private final Model model = new Model();

    @FXML
    private ListManager listManagerController;
    @FXML
    private StatsManager statsManagerController;
    @FXML
    private UnicornManager unicornManagerController;

    public void initialize() {
        listManagerController.configureModel(model);
        statsManagerController.configureModel(model);
        unicornManagerController.configureModel(model);
    }
}
