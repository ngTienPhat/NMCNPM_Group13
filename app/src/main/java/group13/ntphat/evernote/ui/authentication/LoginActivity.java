package group13.ntphat.evernote.ui.authentication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;



public class LoginActivity extends FragmentActivity {
    class MyReceiver extends BroadcastReceiver {
        public int state;

        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            int success = intent.getIntExtra("success", 0);
            state = success;
            if (name.equals("login")) {
                if (success == 1) {
                    //DATA.getAllInfo(context, USER.getInstance().getUserID());
                    Intent intentSuccess = new Intent(LoginActivity.this, MainActivity.class);
                    context.startActivity(intentSuccess);
                }else {
                    Toast.makeText(context, "Wrong password!",
                            Toast.LENGTH_LONG).show();
                }
            }
//        if (name.equals("singup")) {
//            if (success == 1) {
//
//            }else {
//
//            }
//        }
//
//        if (name.equals("getAllInfo")) {
//
//        }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Evernote");

        final EditText editTxtUsername = findViewById(R.id.editTxt_username);
        final EditText editTxtPassword = findViewById(R.id.editTxt_password);
        View btn = findViewById(R.id.btn_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTxtUsername.getText().toString();
                String password = editTxtPassword.getText().toString();
                // Do login
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("DATA");
                registerReceiver(new MyReceiver(), intentFilter);
                DATA.login(getBaseContext(), username, password);
            }
        });
    }
}
