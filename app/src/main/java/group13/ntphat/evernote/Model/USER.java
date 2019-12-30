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

    void addNoteBook(NOTEBOOK notebook) {
        notebooks.add(notebook);
    }
    void removeNoteBook(String notebookID) {
        for (int i = 0; i < notebooks.size(); i++) {
            if (notebookID == notebooks.get(i).getNotebookID()) {
                notebooks.remove(i);
                break;
            }
        }
    }

    ArrayList<NOTE> getAllNote() {
        ArrayList<NOTE> ans = new ArrayList<>();
        for (int i = 0; i < notebooks.size(); i++) {
            NOTEBOOK notebook = notebooks.get(i);
            ans.addAll(notebook.notes);
        }
        return ans;
    }

    ArrayList<NOTEBOOK> getAllNoteBook() {
        return notebooks;
    }

    Map getAllTag() {
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

    ArrayList<NOTE> getNoteTag(String Tag) {
        ArrayList<NOTE> ans = new ArrayList<>();
        for (int i = 0; i < notebooks.size(); i++) {
            ArrayList<NOTE> notes = notebooks.get(i).notes;
            for (int j = 0; j < notes.size(); j++) {
                if (notes.get(j).isHasTag(Tag)) ans.add(notes.get(j));
            }
        }
        return ans;
    }

}
