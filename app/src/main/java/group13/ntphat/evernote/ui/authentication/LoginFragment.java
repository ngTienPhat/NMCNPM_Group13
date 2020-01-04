package group13.ntphat.evernote.ui.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.R;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText editTxtUsername = root.findViewById(R.id.editTxt_username);
        final EditText editTxtPassword = root.findViewById(R.id.editTxt_password);

        View btn = root.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTxtUsername.getText().toString();
                String password = editTxtPassword.getText().toString();
                DATA.login(getContext(), username, password);
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
}
