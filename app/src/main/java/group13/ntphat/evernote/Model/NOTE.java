package group13.ntphat.evernote.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NOTE {
    private String noteID;
    private String notebookID;
    private String fullName;
    private String title;
    private String createDate;
    private String content;
    private Double gpsLong;
    private Double gpsLat;
    private Boolean isShare;
    public ArrayList<String> tags;

    public NOTE() {
        gpsLat = 0.0;
        gpsLong = 0.0;
        isShare = false;
        tags = new ArrayList<>();
    }
    public NOTE(String noteID, String notebookID, String fullName, String title, String createDate,
                String content, Double gpsLong, Double gpsLat) {
        this.noteID = noteID;
        this.notebookID = notebookID;
        this.fullName = fullName;
        this.title = title;
        this.createDate = createDate;
        this.gpsLong = gpsLong;
        this.gpsLat = gpsLat;
        this.content = content;
        this.isShare = false;
        this.tags = new ArrayList<>();
    }
    public NOTE(String noteID, String notebookID, String fullName, String title, String createDate,
                String content) {
        this.noteID = noteID;
        this.notebookID = notebookID;
        this.fullName = fullName;
        this.title = title;
        this.createDate = createDate;
        this.content = content;
        this.gpsLong = 0.0;
        this.gpsLat = 0.0;
        this.isShare = false;
        this.tags = new ArrayList<>();
    }
    public NOTE(JSONObject noteJSON) throws JSONException {
        noteID = noteJSON.getString("noteid");
        title = noteJSON.getString("title");
        createDate = noteJSON.getString("createddate");
        content = noteJSON.getString("contentfile");
        fullName = noteJSON.getString("fullname");
        gpsLong = noteJSON.getDouble("longtitude");
        gpsLat = noteJSON.getDouble("latitude");
        isShare = noteJSON.getBoolean("shared");

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
        body.put("fullname", fullName);
        body.put("longtitude", gpsLong);
        body.put("latitude", gpsLat);
        body.put("shared", isShare);

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String author) {
        this.fullName = author;
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

    public void turnOnShare() {
        isShare = true;
    }
    public void turnOffShare() {
        isShare = false;
    }
    public Boolean getIsShare() { return isShare;}
}