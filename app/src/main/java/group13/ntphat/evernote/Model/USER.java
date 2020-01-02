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
            INSTANCE = new USER("", "haituan134", "haituan134@gmail.com",
                    1, "10/5/2019", "haituan134");
            NOTEBOOK notebook = new NOTEBOOK("345", "NMCNPM", "10/5/2019");
            NOTE note = new NOTE("678", "345", "Báo cáo", "10/5", "");
            NOTE note1 = new NOTE("789", "345", "Thực hành", "10/5", "");
            note.addTag("a");
            note.addTag("b");
            note1.addTag("b");
            notebook.addNote(note);
            notebook.addNote(note1);
            INSTANCE.addNoteBook(notebook);
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
    public void updateNotebook(NOTEBOOK notebook) {
        removeNoteBook(notebook.getNotebookID());
        addNoteBook(notebook);
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
    public void updateNote(String notebookID, NOTE note) {
        removeNote(notebookID, note.getNoteID());
        addNote(notebookID, note);
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
        this.addNote(newNotebookID, noteChanged);
    }
}
