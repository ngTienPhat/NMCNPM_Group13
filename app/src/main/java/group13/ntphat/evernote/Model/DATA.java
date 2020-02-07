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
import java.util.ArrayList;

import group13.ntphat.evernote.ui.notebook.NotebookFragment;
import group13.ntphat.evernote.ui.notes.NotesFragment;

public class DATA {
    static public String link = "https://project-nmcnpm.herokuapp.com/";

    static public void sendIntendBroadcast(Context context, String name, int success) {
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
                                String avatar = userJSON.getString("avatar");

                                USER.getInstance().init(userID, fullName, userEmail, accountLevel,
                                        memberSince, userName, avatar);
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
        String url = link + "users/create";

        final JSONObject body = new JSONObject();
        body.put("fullname", fullName);
        body.put("username", userName);
        body.put("useremail", userEmail);
        body.put("password", password);

        final String username = userName;
        final String pass = password;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJSON = new JSONObject(response);
                            int status = userJSON.getInt("status");

                            Intent intent = new Intent("DATA");
                            intent.putExtra("name", "signup");
                            intent.putExtra("success", status);
                            intent.putExtra("username", username);
                            intent.putExtra("password", pass);
                            context.sendBroadcast(intent);
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
    static public void forgotPassword(final Context context, String userName, String userEmail) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "/users/forgotPassword";

        final JSONObject body = new JSONObject();
        try {
            body.put("username", userName);
            body.put("useremail", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJSON = new JSONObject(response);
                            int status = userJSON.getInt("status");

                            DATA.sendIntendBroadcast(context, "forgotPassword", status);
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
    static public void updateInfo(final Context context, final String fullName, String userEmail, String userPassword) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/change";

        final JSONObject body = new JSONObject();
        try {
            if (fullName != null) {
                USER.getInstance().setFullName(fullName);
                body.put("fullname", fullName);
            }
            else {
                body.put("fullname", "");
            }

            if (userEmail != null) {
                USER.getInstance().setUserEmail(userEmail);
                body.put("useremail", userEmail);
            }
            else {
                body.put("useremail", "");
            }
            if (userPassword != null) {
                body.put("userpassword", userPassword);
            }
            else {
                body.put("userpassword", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJSON = new JSONObject(response);
                            int status = userJSON.getInt("status");

                            DATA.sendIntendBroadcast(context, "updateInfo", status);
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
                                NOTEBOOK notebook = new NOTEBOOK(notebooks.getJSONObject(i));
                                USER.getInstance().heper_addNoteBook(notebook);
                            }
                            NotesFragment.updateListNotes();
                            //sendIntendBroadcast(context,"getAllInfo", 1);
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
    static public void getNoteByGPS(final Context context, Double gpsLong, Double gpsLat) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + "/getNoteByGPS/" + gpsLong + "+" + gpsLat;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray notesJSON = new JSONArray(response);
                            ArrayList<NOTE> notes = new ArrayList<>();
                            for (int i = 0; i < notesJSON.length(); i++) {
                                NOTE note = new NOTE(notesJSON.getJSONObject(i));
                                notes.add(note);
                            }
                            USER.getInstance().helper_updateNoteByGPS(notes);

                            //NotesFragment.updateListNotes();
                            //sendIntendBroadcast(context,"getAllInfo", 1);
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

    static public void createNotebook(final Context context, final String nameNotebook) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/notebook";

        final JSONObject body = new JSONObject();
        body.put("notebookname", nameNotebook);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject notebookJSON = new JSONObject(response);
                            NOTEBOOK notebook = new NOTEBOOK(notebookJSON);

                            USER.getInstance().heper_addNoteBook(notebook);
                            USER.getInstance().setNewNotebookID(notebook.getNotebookID());
                            NotebookFragment.updateListNotebooks();
                            //DATA.sendIntendBroadcast(context, "signup", status);
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
                        } catch (JSONException | UnsupportedEncodingException e) {
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
    static public void updateNameNotebook(final Context context, final String notebookID,
                                          String nameNotebook) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/notebook/" + notebookID;

        final JSONObject body = new JSONObject();
        body.put("notebookname", nameNotebook);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        NotebookFragment.updateListNotebooks();
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
                        } catch (JSONException | UnsupportedEncodingException e) {
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
    static public void removeNotebook(final Context context, final String notebookID) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/notebook/" + notebookID;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        NotebookFragment.updateListNotebooks();
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
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        queue.add(stringRequest);
    }


    static public void createNote(final Context context, final String notebookID, final  String nameNote) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/note";

        final JSONObject body = new JSONObject();
        body.put("notebookid", notebookID);
        body.put("notename", nameNote);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject noteJSON = new JSONObject(response);
                            NOTE note = new NOTE(noteJSON);
                            note.setNotebookID(notebookID);

                            USER.getInstance().helper_addNote(notebookID, note);
                            USER.getInstance().setNewNoteID(note.getNoteID());
                            NotesFragment.updateListNotes();
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
                        } catch (JSONException | UnsupportedEncodingException e) {
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
    static public void updateNote(final Context context, final NOTE note) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/note/" + note.getNoteID();

        final JSONObject body = note.toJSONObject();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        NotesFragment.updateListNotes();
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
                        } catch (JSONException | UnsupportedEncodingException e) {
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
    static public void removeNote(final Context context, final String notebookID, final String noteID) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = link + "users/" + USER.getInstance().getUserID() + "/note/" + noteID;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        NotesFragment.updateListNotes();
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
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        queue.add(stringRequest);
    }
}