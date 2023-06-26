package todoyaf.todoyaf;

public abstract class BaseController {

    protected Model model;

    public void configureModel(Model model) {
        this.model = model;
    }

}
