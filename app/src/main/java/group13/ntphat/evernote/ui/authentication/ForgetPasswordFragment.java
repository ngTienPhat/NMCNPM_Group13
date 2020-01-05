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

public class ForgetPasswordFragment extends Fragment {
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forget_password, container, false);
        final EditText editTxtUsername = root.findViewById(R.id.editTxt_username);
        final EditText editTxtEmail = root.findViewById(R.id.editTxt_email);

        View btn = root.findViewById(R.id.btn_reset_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTxtUsername.getText().toString();
                String email = editTxtEmail.getText().toString();
                // do reset password
                Toast.makeText(getActivity(), "Đã gửi email đặt lại mật khẩu!",
                        Toast.LENGTH_LONG).show();
            }
        });

        View txtLogin = root.findViewById(R.id.txt_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.SwapFragment(AuthenticationActivity.FRAGMENT_LOGIN);
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
