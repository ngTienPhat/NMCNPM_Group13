package group13.ntphat.evernote.ui.notebook;

public class NotebookItem {
    private String name;
    private int numberOfNotes;

    public NotebookItem(String name, int numberOfNotes) {
        this.name = name;
        this.numberOfNotes = numberOfNotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfNotes() {
        return numberOfNotes;
    }

    public void setNumberOfNotes(int numberOfNotes) {
        this.numberOfNotes = numberOfNotes;
    }
}
