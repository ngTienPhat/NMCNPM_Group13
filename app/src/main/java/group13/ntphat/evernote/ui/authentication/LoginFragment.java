package group13.ntphat.evernote.ui.authentication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.R;



public class LoginFragment extends Fragment {

    class MyReceiver extends BroadcastReceiver {
        public int state;

        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            int success = intent.getIntExtra("success", 0);
            state = success;
            if (name.equals("login")) {
                if (success == 1) {
                    Intent intentSuccess = new Intent(LoginFragment.this.getContext(), MainActivity.class);
                    context.startActivity(intentSuccess);
                }else {
                    Toast.makeText(LoginFragment.this.getContext(), "Wrong password!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

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
                // Do login
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("DATA");
                LoginFragment.this.getContext().registerReceiver(new MyReceiver(), intentFilter);
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
