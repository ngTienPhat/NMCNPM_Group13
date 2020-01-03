package group13.ntphat.evernote.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NOTEBOOK {
    private String notebookID;
    private String nameNotebook;
    private String createDate;
    public ArrayList<NOTE> notes;

    public NOTEBOOK(String notebookID, String nameNoteBook, String createDate) {
        this.notebookID = notebookID;
        this.nameNotebook = nameNoteBook;
        this.createDate = createDate;
        notes = new ArrayList<>();
    }

    public NOTEBOOK(JSONObject notebookJSON) throws JSONException {
        notebookID = notebookJSON.getString("notebookid");
        nameNotebook = notebookJSON.getString("name");
        createDate = notebookJSON.getString("createdate");

        JSONArray notesJSON = notebookJSON.getJSONArray("notes");
        notes = new ArrayList<>();
        for (int j = 0; j < notesJSON.length(); j++) {
            NOTE note = new NOTE(notesJSON.getJSONObject(j));
            note.setNotebookID(notebookID);
            notes.add(note);
        }
    }

    public String getNotebookID() {
        return notebookID;
    }

    public String getNameNoteBook() {
        return nameNotebook;
    }

    public void setNameNoteBook(String nameNoteBook) {
        this.nameNotebook = nameNoteBook;
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
}
