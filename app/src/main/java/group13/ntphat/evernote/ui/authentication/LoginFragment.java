package group13.ntphat.evernote.ui.authentication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.R;

public class LoginFragment extends Fragment {

    private EditText editTxtUsername;
    private EditText editTxtPassword;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        this.editTxtUsername = root.findViewById(R.id.editTxt_username);
        this.editTxtPassword = root.findViewById(R.id.editTxt_password);

        this.editTxtPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            doLogin();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        View btn = root.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        View txtSignUp = root.findViewById(R.id.txt_signUp);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.SwapFragment(AuthenticationActivity.FRAGMENT_SIGN_UP);
            }
        });
        return root;
    }

    private void doLogin() {
        String username = editTxtUsername.getText().toString();
        String password = editTxtPassword.getText().toString();
        if (username.compareTo("") == 0) {
            Toast.makeText(getContext(), "Hãy nhập tên đăng nhập!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (password.compareTo("") == 0) {
            Toast.makeText(getContext(), "Hãy nhập mật khẩu!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        DATA.login(getContext(), username, password);
    }
}
