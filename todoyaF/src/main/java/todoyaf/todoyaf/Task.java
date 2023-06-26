package todoyaf.todoyaf;

public class Task {

    String name;
    String note;
    private static final String STD_NOTE = "Please enter a note \nbefore you save it :) \nYou can use NOTE to save the note";


    public Task(String name){
        this.name=name;
        this.note=STD_NOTE;
    }

    public void updateNote(String newNote){
        this.note=newNote;
    }

    @Override
    public String toString() {
        return name;
    }
}
