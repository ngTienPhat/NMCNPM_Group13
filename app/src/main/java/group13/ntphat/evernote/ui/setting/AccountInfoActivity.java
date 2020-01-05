package group13.ntphat.evernote.ui.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.authentication.AuthenticationActivity;

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
                dialog.setContentView(R.layout.dialog_change_info);
                dialog.show();

                final EditText edtFullname = dialog.findViewById(R.id.editTxt_fullname);
                edtFullname.setText(USER.getInstance().getFullName());

                final EditText edtEmail = dialog.findViewById(R.id.editTxt_email);
                edtEmail.setText(USER.getInstance().getUserEmail());

                View btnConfirm = dialog.findViewById(R.id.btn_confirm);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fullname = edtFullname.getText().toString();
                        String email = edtEmail.getText().toString();

                        if (fullname.compareTo(USER.getInstance().getFullName()) == 0)
                            fullname = null;
                        if (email.compareTo(USER.getInstance().getUserEmail()) == 0)
                            email = null;

                        // do change info here
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
                final Dialog dialog = new Dialog(AccountInfoActivity.this);
                dialog.setContentView(R.layout.dialog_change_password);
                dialog.show();

                final EditText edtNewPass = dialog.findViewById(R.id.editTxt_newPassword);
                final EditText edtRetypeNewPass = dialog.findViewById(R.id.editTxt_retypeNewPassword);

                View btnConfirm = dialog.findViewById(R.id.btn_confirm);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newPass = edtNewPass.getText().toString();
                        String retypeNewPass = edtRetypeNewPass.getText().toString();

                        if (newPass.compareTo(retypeNewPass) != 0) {
                            Toast.makeText(AccountInfoActivity.this, "Mật khẩu không khớp, xin nhập lại!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // do change password here
                            dialog.cancel();
                        }
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
