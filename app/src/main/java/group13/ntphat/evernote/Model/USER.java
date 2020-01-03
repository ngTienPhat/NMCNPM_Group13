package group13.ntphat.evernote.Model;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.gc;

public class USER {
    private String userID;
    private String fullName;

    public String getUserID() {
        return userID;
    }
    public String getFullName() {
        return fullName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public int getAccountLevel() {
        return accountLevel;
    }
    public String getMemberSince() {
        return memberSince;
    }
    public String getUserName() {
        return userName;
    }

    private String userEmail;
    private int accountLevel;
    private String memberSince;
    private String userName;
    private ArrayList<NOTEBOOK> notebooks;

    private String newNoteID;
    private String newNotebookID;

    private USER() {
        notebooks = new ArrayList<>();
    }
    private USER(String userID, String fullName, String userEmail, int accountLevel,
                 String memberSince, String userName) {
        this.userID = userID;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.accountLevel = accountLevel;
        this.memberSince = memberSince;
        this.userName = userName;
        this.notebooks = new ArrayList<>();
    }

    private static USER INSTANCE = null;
    public static USER getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new USER();
        }
        return INSTANCE;
    }

    public void init(String userID, String fullName, String userEmail, int accountLevel,
                     String memberSince, String userName) {
        this.userID = userID;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.accountLevel = accountLevel;
        this.memberSince = memberSince;
        this.userName = userName;
        this.notebooks = new ArrayList<>();
    }

    public void remove() {
        this.userID = "";
        this.fullName = "";
        this.userEmail = "";
        this.accountLevel = 0;
        this.memberSince = "";
        this.userName = "";
        this.notebooks = new ArrayList<>();
        gc();
    }

    public String getNewNoteID() {
        return newNoteID;
    }
    public void setNewNoteID(String newNoteID) {
        this.newNoteID = newNoteID;
    }

    public String getNewNotebookID() {
        return newNotebookID;
    }
    public void setNewNotebookID(String newNotebookID) {
        this.newNotebookID = newNotebookID;
    }

    public ArrayList<NOTEBOOK> getAllNoteBook() {
        return notebooks;
    }
    public NOTEBOOK getNoteBook(String notebookID) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            return notebook;
        }
        return null;
    }
    public void heper_addNoteBook(NOTEBOOK notebook) {
        notebooks.add(notebook);
    }
    public void addNoteBook(Context context, String nameNotebook) throws JSONException {
        DATA.createNotebook(context, nameNotebook);
    }
    public void heper_removeNoteBook(String notebookID) {
        for (int i = 0; i < notebooks.size(); i++) {
            if (notebookID == notebooks.get(i).getNotebookID()) {
                notebooks.remove(i);
                break;
            }
        }
    }
    public void removeNoteBook(Context context, String notebookID) {
        heper_removeNoteBook(notebookID);
        DATA.removeNotebook(context, notebookID);
    }
    public void updateNotebook(Context context, NOTEBOOK notebook) throws JSONException {
        heper_removeNoteBook(notebook.getNotebookID());
        heper_addNoteBook(notebook);
        DATA.updateNameNotebook(context, notebook.getNotebookID(), notebook.getNameNoteBook());
    }

    public ArrayList<NOTE> getAllNote() {
        ArrayList<NOTE> ans = new ArrayList<>();
        for (int i = 0; i < notebooks.size(); i++) {
            NOTEBOOK notebook = notebooks.get(i);
            ans.addAll(notebook.notes);
        }
        return ans;
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
    public void heper_addNote(String notebookID, NOTE note) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            notebook.notes.add(note);
            break;
        }
    }
    public void addNote(Context context, String notebookID, String nameNote) throws JSONException {
        DATA.createNote(context, notebookID, nameNote);
    }
    public void heper_removeNote(String notebookID, String noteID) {
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
    public void removeNote(Context context, String notebookID, String noteID) {
        heper_removeNote(notebookID, noteID);
        DATA.removeNote(context, notebookID, noteID);
    }
    public void updateNote(Context context, String notebookID, NOTE note) throws JSONException {
        heper_removeNote(notebookID, note.getNoteID());
        heper_addNote(notebookID, note);
        DATA.updateNote(context, note);
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
        this.heper_addNote(newNotebookID, noteChanged);
    }
}
