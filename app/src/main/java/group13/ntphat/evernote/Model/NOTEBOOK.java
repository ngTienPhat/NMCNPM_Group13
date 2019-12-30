package group13.ntphat.evernote.Model;

import java.util.ArrayList;

public class NOTEBOOK {
    private String notebookID;
    private String nameNoteBook;
    private String createDate;
    public ArrayList<NOTE> notes;

    public String getNotebookID() {
        return notebookID;
    }

    public String getNameNoteBook() {
        return nameNoteBook;
    }

    public void setNameNoteBook(String nameNoteBook) {
        this.nameNoteBook = nameNoteBook;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void addNote(NOTE note) {
        notes.add(note);
    }
    public void removeNote(String noteID) {
        for (int i = 0; i < notes.size(); i++) {
            if (noteID == notes.get(i).getNoteID()) {
                notes.remove(i);
                break;
            }
        }
    }

}
