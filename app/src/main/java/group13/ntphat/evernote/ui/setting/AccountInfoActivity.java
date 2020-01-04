package group13.ntphat.evernote.ui.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.authentication.AuthenticationActivity;
import group13.ntphat.evernote.ui.notes.ViewNoteActivity;

public class AccountInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        setTitle("Thông tin tài khoản");

        TextView fullname = (TextView) findViewById(R.id.txt_fullname);
        TextView email = (TextView) findViewById(R.id.txt_email);
        fullname.setText(USER.getInstance().getFullName());
        email.setText(USER.getInstance().getUserEmail());

        View btn = findViewById(R.id.btn_change_name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AccountInfoActivity.this);
                dialog.setContentView(R.layout.dialog_change_fullname);
                dialog.show();

                final EditText newFullname = dialog.findViewById(R.id.editTxt_fullname);
                newFullname.setText(USER.getInstance().getFullName());
                View btnConfirm = dialog.findViewById(R.id.btn_confirm);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView fullname = (TextView) findViewById(R.id.txt_fullname);
                        fullname.setText(newFullname.getText());
                        dialog.cancel();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        btn = findViewById(R.id.btn_change_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn = findViewById(R.id.btn_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountInfoActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                intent = new Intent("CLOSE_ALL");
                sendBroadcast(intent);
                finish();
            }
        });
    }
}
