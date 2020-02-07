package group13.ntphat.evernote.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NOTE {
    private String noteID;
    private String notebookID;
    private String author;
    private String title;
    private String createDate;
    private String content;
    private Double gpsLong;
    private Double gpsLat;
    public ArrayList<String> tags;

    public NOTE() {
        tags = new ArrayList<>();
    }
    public NOTE(String noteID, String notebookID, String author, String title, String createDate,
                String content, Double gpsLong, Double gpsLat) {
        this.noteID = noteID;
        this.notebookID = notebookID;
        this.author = author;
        this.title = title;
        this.createDate = createDate;
        this.gpsLong = gpsLong;
        this.gpsLat = gpsLat;
        this.content = content;
        this.tags = new ArrayList<>();
    }
    public NOTE(String noteID, String notebookID, String author, String title, String createDate,
                String content) {
        this.noteID = noteID;
        this.notebookID = notebookID;
        this.author = author;
        this.title = title;
        this.createDate = createDate;
        this.content = content;
        this.tags = new ArrayList<>();
    }
    public NOTE(JSONObject noteJSON) throws JSONException {
        noteID = noteJSON.getString("noteid");
        title = noteJSON.getString("title");
        createDate = noteJSON.getString("createddate");
        content = noteJSON.getString("contentfile");
        author = noteJSON.getString("author");
        gpsLong = noteJSON.getDouble("long");
        gpsLat = noteJSON.getDouble("lat");

        JSONArray tagsJSON = noteJSON.getJSONArray("tags");
        tags = new ArrayList<>();
        for (int k = 0; k < tagsJSON.length(); k++) {
            String tag = tagsJSON.getJSONObject(k).getString("tagname");
            tags.add(tag);
        }
    }
    JSONObject toJSONObject() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("contentfile", content);
        body.put("title", title);
        body.put("notebookid", notebookID);
        body.put("author", author);
        body.put("long", gpsLong);
        body.put("lat", gpsLat);

        JSONArray tagsJSON = new JSONArray();
        for (int i = 0; i < tags.size(); i++) {
            JSONObject tag = new JSONObject();
            tag.put("tagname", tags.get(i));

            tagsJSON.put(tag);
        }

        body.put("tags", tagsJSON);

        return body;
    }


    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(Double gpsLong) {
        this.gpsLong = gpsLong;
    }

    public Double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public void addTag(String tag) {
        tags.add(0, tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

}