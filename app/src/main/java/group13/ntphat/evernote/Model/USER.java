package group13.ntphat.evernote.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class USER {
    private String userID;
    private String fullName;
    private String userEmail;
    private int accountLevel;
    private String memberSince;
    private String userName;
    private ArrayList<NOTEBOOK> notebooks;

    private USER() {
        notebooks = new ArrayList<NOTEBOOK>();
    }

    private static USER INSTANCE = null;
    public static USER getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new USER();
        }
        return INSTANCE;
    }

    public ArrayList<NOTE> getAllNote() {
        ArrayList<NOTE> ans = new ArrayList<>();
        for (int i = 0; i < notebooks.size(); i++) {
            NOTEBOOK notebook = notebooks.get(i);
            ans.addAll(notebook.notes);
        }
        return ans;
    }

    public ArrayList<NOTEBOOK> getAllNoteBook() {
        return notebooks;
    }

    public Map getAllTag() {
        Map ans = new HashMap<String, Integer>();
        ArrayList<NOTE> notes = getAllNote();
        for (int i = 0; i < notes.size(); i++) {
            NOTE note = notes.get(i);
            for (int j = 0; j < note.tags.size(); j++) {
                int value = 1;
                String tag = note.tags.get(j);
                if (ans.containsKey(tag)) {
                    value = (int) ans.get(tag) + 1;
                    ans.remove(tag);
                }
                ans.put(tag, value);
            }
        }

        return ans;
    }

    public ArrayList<NOTE> getNoteTag(String Tag) {
        ArrayList<NOTE> ans = new ArrayList<>();
        for (int i = 0; i < notebooks.size(); i++) {
            ArrayList<NOTE> notes = notebooks.get(i).notes;
            for (int j = 0; j < notes.size(); j++) {
                if (notes.get(j).isHasTag(Tag)) ans.add(notes.get(j));
            }
        }
        return ans;
    }

    public NOTEBOOK getNoteBook(String notebookID) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            return notebook;
        }
        return null;
    }

    public NOTE getNote(String notebookID, String noteID) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            for (NOTE note:notebook.notes) {
                if (!note.getNoteID().equals(noteID)) continue;
                return note;
            }
        }
        return null;
    }


    public void addNoteBook(NOTEBOOK notebook) {
        notebooks.add(notebook);
    }
    public void removeNoteBook(String notebookID) {
        for (int i = 0; i < notebooks.size(); i++) {
            if (notebookID == notebooks.get(i).getNotebookID()) {
                notebooks.remove(i);
                break;
            }
        }
    }

    public void addNote(String notebookID, NOTE note) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            notebook.notes.add(note);
            break;
        }
    }
    public void removeNote(String notebookID, String noteID) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            for (int i = 0; i < notebook.notes.size(); i++) {
                NOTE note = notebook.notes.get(i);
                if (note.getNoteID().equals(noteID)) {
                    notebook.notes.remove(i);
                    break;
                }
            }
        }
    }

    public void addTag(String notebookID, String noteID, String tag) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            for (NOTE note:notebook.notes) {
                if (!note.getNoteID().equals(noteID)) continue;
                note.tags.add(tag);
                break;
            }
            break;
        }
    }
    public void removeTag(String notebookID, String noteID, String tag) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            for (NOTE note:notebook.notes) {
                if (!note.getNoteID().equals(noteID)) continue;
                note.tags.remove(tag);
                break;
            }
            break;
        }
    }

    public void changeNotebook(String noteID, String oldNotebookID, String newNotebookID) {
        NOTE noteChanged = new NOTE();
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(oldNotebookID)) continue;
            for (NOTE note:notebook.notes) {
                if (!note.getNoteID().equals(noteID)) continue;
                noteChanged = note;
                break;
            }
            notebook.notes.remove(noteChanged);
            break;
        }

        noteChanged.setNotebookID(newNotebookID);
        this.addNote(newNotebookID, noteChanged);
    }

    public void download() {

    }

    public void upload() {

    }
}
