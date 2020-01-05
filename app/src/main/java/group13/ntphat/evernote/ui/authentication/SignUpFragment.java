package group13.ntphat.evernote.ui.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.R;

public class SignUpFragment extends Fragment {
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        final EditText editTxtFullname = root.findViewById(R.id.editTxt_fullname);
        final EditText editTxtUsername = root.findViewById(R.id.editTxt_username);
        final EditText editTxtEmail = root.findViewById(R.id.editTxt_email);
        final EditText editTxtPassword = root.findViewById(R.id.editTxt_password);
        final EditText editTxtRetypePassword = root.findViewById(R.id.editTxt_retypePassword);

        View btn = root.findViewById(R.id.btn_signUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = editTxtFullname.getText().toString();
                String username = editTxtUsername.getText().toString();
                String email = editTxtEmail.getText().toString();
                String password = editTxtPassword.getText().toString();
                String retypePassword = editTxtRetypePassword.getText().toString();
                if (password.compareTo(retypePassword) != 0) {
                    Toast.makeText(inflater.getContext(), "Mật khẩu không khớp, mời nhập lại!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        DATA.signup(getContext(), username, email, fullname, password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        View txtLogin = root.findViewById(R.id.txt_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.SwapFragment(AuthenticationActivity.FRAGMENT_LOGIN);
            }
        });
        return root;
    }

}
