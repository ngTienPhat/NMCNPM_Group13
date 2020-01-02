package group13.ntphat.evernote.Model;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DATA {
    static private String link = "https://b24dbbcb.ngrok.io/";

    static private void sendIntendBroadcast(Context context, String name, int success) {
        Intent intent = new Intent();
        intent.setAction("DATA");
        intent.putExtra("name", name);
        intent.putExtra("success", success);
        context.sendBroadcast(intent);
    }

    static public void login(final Context context, String username, String userpwd) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/signIn?username=" + username + "&userpwd=" + userpwd;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJSON = new JSONObject(response);
                            int logIn = userJSON.getInt("logIn");
                            if (logIn == 1) {
                                String userID = userJSON.getString("userid");
                                String fullName = userJSON.getString("fullname");
                                String userEmail = userJSON.getString("useremail");
                                int accountLevel = userJSON.getInt("accountlevel");
                                String memberSince = userJSON.getString("membersince");
                                String userName = userJSON.getString("username");

                                USER.getInstance().init(userID, fullName, userEmail, accountLevel,
                                        memberSince, userName);
                            }

                            DATA.sendIntendBroadcast(context, "login", logIn);
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

    static public void signup(final Context context, final String userName, final String userEmail,
                              final String fullName, String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "student";

        final JSONObject body = new JSONObject();
        body.put("fullname", fullName);
        body.put("username", userName);
        body.put("useremail", userEmail);
        body.put("password", password);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJSON = new JSONObject(response);
                            int status = userJSON.getInt("status");
                            if (status == 1) {
                                String userID = userJSON.getString("userid");
                                USER.getInstance().init(userID, fullName, userEmail, 0,
                                        "", userName);
                            }

                            DATA.sendIntendBroadcast(context, "signup", status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg;
                        try {
                            if (error.networkResponse.statusCode == 500) {
                                msg = "Lỗi hệ thống";
                            } else {
                                JSONObject json = new JSONObject(new String(error.networkResponse.data, "utf-8"));
                                msg = json.getString("message");
                            }
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return body.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(stringRequest);

    }

    static public void getAllInfo(final Context context, String userID) {
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

                                    JSONArray tags = noteJSON.getJSONArray("tags");
                                    for (int k = 0; k < tags.length(); k++) {
                                        String tag = tags.getJSONObject(k).getString("tagname");
                                        note.addTag(tag);
                                    }
                                    notebook.addNote(note);
                                }

                                USER.getInstance().addNoteBook(notebook);
                            }

                            sendIntendBroadcast(context,"getAllInfo", 1);
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