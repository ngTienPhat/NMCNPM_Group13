package group13.ntphat.evernote.Model;

import java.util.ArrayList;

public class NOTE {
    private String noteID;
    private String notebookName;
    private String title;
    private String createDate;
    private String dir;
    public ArrayList<String> tags;

    public String getNoteID() {
        return noteID;
    }

    public String getNotebookName() {
        return notebookName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    boolean isHasTag(String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i) == tag) return true;
        }
        return false;
    }

    void addTag(String tag) {
        tags.add(tag);
    }

    void remove(String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tag == tags.get(i)) {
                tags.remove(i);
                break;
            }
        }
    }
}