package group13.ntphat.evernote.Model;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DATA {
    static private String link = "https://be4808e3.ngrok.io/";

    static public void login(Context context, String username, String userpwd) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/signIn?username=" + username + "&userpwd=" + userpwd;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJSON = new JSONObject(response);
                            String userID = userJSON.getString("userid");
                            String fullName = userJSON.getString("fullname");
                            String userEmail = userJSON.getString("useremail");
                            int accountLevel = userJSON.getInt("accountlevel");
                            String memberSince = userJSON.getString("membersince");
                            String userName = userJSON.getString("username");

                            USER.getInstance().init(userID, fullName, userEmail, accountLevel,
                                    memberSince, userName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //textView.setText("That didn't work!");
                        Log.e("ERRR", String.valueOf(error.networkResponse.statusCode));
                    }
                }
        );

        queue.add(stringRequest);
    }

    static public void signup(String username, String userEmial, String fullName, String password) {


    }

    static public void getAllInfo(Context context, String userID) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + userID + "/getAllInfo";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray notebooks = new JSONArray(response);

                            for (int i = 0; i < notebooks.length(); i++) {
                                JSONObject notebookJSON = notebooks.getJSONObject(i);
                                String notebookID = notebookJSON.getString("notebookid");
                                String nameNotebook = notebookJSON.getString("name");
                                String createDate = notebookJSON.getString("createdate");
                                NOTEBOOK notebook = new NOTEBOOK(notebookID, nameNotebook, createDate);

                                JSONArray notes = notebooks.getJSONObject(i).getJSONArray("notes");
                                for (int j = 0; j < notes.length(); j++) {
                                    JSONObject noteJSON = notes.getJSONObject(j);
                                    String noteID = noteJSON.getString("noteid");
                                    String title = noteJSON.getString("title");
                                    String noteCreateDate = noteJSON.getString("createddate");
                                    String content = noteJSON.getString("contentfile");

                                    NOTE note = new NOTE(noteID, notebookID, title, noteCreateDate, content);
                                    notebook.addNote(note);
                                }

                                USER.getInstance().addNoteBook(notebook);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //textView.setText("That didn't work!");
                        Log.e("ERRR", String.valueOf(error.networkResponse.statusCode));
                    }
                }
        );

        queue.add(stringRequest);
    }

    static public void upload() {

    }
}