package group13.ntphat.evernote.ui.setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.InputStream;
import java.net.URL;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.authentication.AuthenticationActivity;
import xute.markdeditor.api.ImageUploader;
import xute.markdeditor.utilities.FilePathUtils;

public class AccountInfoActivity extends AppCompatActivity {
    private BroadcastReceiver changeInfoReceiver;
    private TextView txtFullname;
    private TextView txtEmail;
    private final int REQUEST_IMAGE_SELECTOR = 8;

    private void addChangeInfoReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DATA");
        this.changeInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String name = intent.getStringExtra("name");
                int success = intent.getIntExtra("success", 0);
                if (name.equals("updateInfo")) {
                    if (success == 1) {
                        String fullname = USER.getInstance().getFullName();
                        if (fullname.compareTo(txtFullname.getText().toString()) != 0) {
                            txtFullname.setText(fullname);
                        }
                        String email = USER.getInstance().getUserEmail();
                        if (email.compareTo(txtEmail.getText().toString()) != 0) {
                            txtEmail.setText(email);
                        }
                        Toast.makeText(AccountInfoActivity.this, "Thao tác thành công!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        registerReceiver(this.changeInfoReceiver, intentFilter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        setTitle("Thông tin tài khoản");
        this.addChangeInfoReceiver();

        this.txtFullname = (TextView) findViewById(R.id.txt_fullname);
        this.txtEmail = (TextView) findViewById(R.id.txt_email);
        this.txtFullname.setText(USER.getInstance().getFullName());
        this.txtEmail.setText(USER.getInstance().getUserEmail());

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
                        DATA.updateInfo(AccountInfoActivity.this, fullname, email, null);
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
                            DATA.updateInfo(AccountInfoActivity.this, null, null, newPass);
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

        btn = findViewById(R.id.btn_set_avatar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(this.changeInfoReceiver);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_SELECTOR) {
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                final String filePath = FilePathUtils.getPath(this, uri);

                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> uploadAvatar = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ImageUploader imageUploader = new ImageUploader();
                        imageUploader.uploadImage(AccountInfoActivity.this, filePath, USER.getInstance().getUserID());
                        return null;
                    }
                };
                uploadAvatar.execute();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_SELECTOR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                    //Toast.makeText()"Permission not granted to access images.");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_SELECTOR);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_SELECTOR);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
