package group13.ntphat.evernote.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.R;

public class SignUpActivity extends AppCompatActivity {
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
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
