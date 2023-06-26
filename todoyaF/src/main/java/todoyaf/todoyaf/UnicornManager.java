package todoyaf.todoyaf;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UnicornManager extends BaseController {

    @FXML
    TextArea notes;

    @FXML
    ImageView unicornView;
    private final String[] unicornImageList = {
        "todoyaF\\src\\main\\resources\\assets\\Einhorn1.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn2.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn3.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn3b.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn3c.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn3d.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn4.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn4b.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn4c.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn5.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn5b.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn5c.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn5d.png",
        "todoyaF\\src\\main\\resources\\assets\\Einhorn6.png"
    };

    Path unicornPath = Paths.get(unicornImageList[0]);

    private Image unicornImage;
    {
        try {
            unicornImage = new Image(new URL("file:///"+unicornPath.toAbsolutePath()).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void configureModel(Model model) {
        unicornView.setImage(unicornImage);
        super.configureModel(model);
        model.winStreak.addListener(this::updateUnicorn);
        model.selectedTask.addListener(this::showNote);
        formatNotes();
    }

    private void showNote(ObservableValue<? extends Task> observableValue, Task oldValue, Task newValue) {
        notes.setText(newValue.note);
    }

    private void updateUnicorn(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if(newValue.intValue()<=unicornImageList.length-1) {
            unicornPath = Paths.get(unicornImageList[newValue.intValue()]);
            {
                try {
                    unicornImage = new Image(new URL("file:///" + unicornPath.toAbsolutePath()).openStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else unicornPath=Paths.get(unicornImageList[unicornImageList.length-1]);
        {
            try {
                unicornImage = new Image(new URL("file:///"+unicornPath.toAbsolutePath()).openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        unicornView.setImage(unicornImage);
    }

    private void formatNotes() {
        notes.setWrapText(true);
        if (notes.getText() == null || notes.getText().trim().equals("")){
            notes.setText("Please enter a note \nbefore you save it :) \nYou can use NOTE to save the note");
        }
    }
    public void addNote() {
        if(!(model.selectedTask.getValue()==null)) {
            model.selectedTask.getValue().updateNote(notes.getText());
        }
        else{
            notes.setText("No list available");
        }
    }
}
