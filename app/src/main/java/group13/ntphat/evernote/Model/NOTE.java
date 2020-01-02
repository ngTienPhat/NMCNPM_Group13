package group13.ntphat.evernote.Model;

import java.util.ArrayList;

public class NOTE {
    private String noteID;
    private String notebookID;
    private String title;
    private String createDate;
    private String content;
    public ArrayList<String> tags;

    public String getNoteID() {
        return noteID;
    }

    public String getNotebookID() {
        return notebookID;
    }

    public void setNotebookID(String notebookID) { this.notebookID = notebookID;}

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHasTag(String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).equals(tag)) return true;
        }
        return false;
    }
}