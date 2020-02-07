package group13.ntphat.evernote.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.gc;

public class USER {
    private String userID;
    private String fullName;
    private String userEmail;
    private int accountLevel;
    private String memberSince;
    private String userName;
    private String avatar;
    private Drawable imgAvatar;
    private ArrayList<NOTEBOOK> notebooks;

    private String newNoteID;
    private String newNotebookID;
    private ArrayList<NOTE> notesByGPS;

    private USER() {
        notebooks = new ArrayList<>();
        notesByGPS = new ArrayList<>();
    }
    private USER(String userID, String fullName, String userEmail, int accountLevel,
                 String memberSince, String userName, String avatar) {
        this.userID = userID;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.accountLevel = accountLevel;
        this.memberSince = memberSince;
        this.userName = userName;
        this.avatar = avatar;
        this.notebooks = new ArrayList<>();
        this.notesByGPS = new ArrayList<>();
    }

    private static USER INSTANCE = null;
    public static USER getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new USER();
        }
        return INSTANCE;
    }

    public void init(String userID, String fullName, String userEmail, int accountLevel,
                     String memberSince, String userName, String avatar) {
        this.userID = userID;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.accountLevel = accountLevel;
        this.memberSince = memberSince;
        this.userName = userName;
        this.avatar = avatar;
        this.imgAvatar = null;
        this.notebooks = new ArrayList<>();
    }

    public void remove() {
        this.userID = "";
        this.fullName = "";
        this.userEmail = "";
        this.accountLevel = 0;
        this.memberSince = "";
        this.userName = "";
        this.avatar = "";
        this.imgAvatar = null;
        this.notebooks = new ArrayList<>();
        gc();
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public int getAccountLevel() {
        return accountLevel;
    }
    public void setAccountLevel(int accountLevel) {
        this.accountLevel = accountLevel;
    }
    public String getMemberSince() {
        return memberSince;
    }
    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public Drawable getImgAvatar() {
        return imgAvatar;
    }
    public void setImgAvatar(Drawable imgAvatar) {
        this.imgAvatar = imgAvatar;
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
        notebooks.add(0, notebook);
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
    public void helper_addNote(String notebookID, NOTE note) {
        for (NOTEBOOK notebook:notebooks) {
            if (!notebook.getNotebookID().equals(notebookID)) continue;
            notebook.notes.add(0, note);
            break;
        }
    }
    public void addNote(Context context, String notebookID, String nameNote) throws JSONException {
        DATA.createNote(context, notebookID, nameNote);
    }
    public void helper_removeNote(String notebookID, String noteID) {
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
        helper_removeNote(notebookID, noteID);
        DATA.removeNote(context, notebookID, noteID);
    }
    public void updateNote(Context context, String notebookID, NOTE note) throws JSONException {
        helper_removeNote(notebookID, note.getNoteID());
        helper_addNote(notebookID, note);
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
                note.addTag(tag);
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
                note.removeTag(tag);
                break;
            }
            break;
        }
    }
    public void removeTag(String tag) {
        for (NOTEBOOK notebook:notebooks) {
            for (NOTE note:notebook.notes) {
                note.removeTag(tag);
            }
        }
    }

    public ArrayList<NOTE> getNotesByGPS() {
        return notesByGPS;
    }

    public void helper_updateNoteByGPS(ArrayList<NOTE> notes) {
        notesByGPS.clear();
        notesByGPS.addAll(notes);
    }
    public void updateNoteByGPS(Context context, Double gpsLong, Double gpsLat) {
        DATA.getNoteByGPS(context, gpsLong, gpsLat);
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
        this.helper_addNote(newNotebookID, noteChanged);
    }

    public void updateAvatar(Context context, String avatarUrl) {
        setAvatar(avatarUrl);
        final Context c = context;
        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Drawable> downloadAvatar = new AsyncTask<String, Void, Drawable>() {
            @Override
            protected Drawable doInBackground(String... urls) {
                try {
                    InputStream is = (InputStream) new URL(urls[0]).getContent();
                    Drawable d = Drawable.createFromStream(is, "avatar");
                    return d;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Drawable drawable) {
                setImgAvatar(drawable);
                DATA.sendIntendBroadcast(c, "updateInfo", 1);
                super.onPostExecute(drawable);
            }
        };
        downloadAvatar.execute(avatarUrl);
    }
}
