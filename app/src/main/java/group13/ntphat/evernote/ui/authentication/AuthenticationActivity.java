package group13.ntphat.evernote.ui.authentication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.R;

public class AuthenticationActivity extends FragmentActivity {

    final public static int FRAGMENT_LOGIN = 1;
    final public static int FRAGMENT_SIGN_UP = 2;

    private static FragmentManager myFragmentManager;
    private static LoginFragment loginFragment;
    private static SignUpFragment signUpFragment;

    private BroadcastReceiver loginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        this.addLoginReceiver();

        myFragmentManager = getSupportFragmentManager();
        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, loginFragment);
            fragmentTransaction.commit();
        }
    }

    public static void SwapFragment(int fragment_id) {
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (fragment_id) {
            case FRAGMENT_LOGIN:
                fragment = loginFragment;
                break;
            case FRAGMENT_SIGN_UP:
                fragment = signUpFragment;
                break;
            default: return;
        }
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void addLoginReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DATA");
        this.loginReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String name = intent.getStringExtra("name");
                int success = intent.getIntExtra("success", 0);
                if (name.equals("login")) {
                    if (success == 1) {
                        Intent intentSuccess = new Intent(AuthenticationActivity.this, MainActivity.class);
                        context.startActivity(intentSuccess);
                        AuthenticationActivity.this.finish();
                    }else {
                        Toast.makeText(AuthenticationActivity.this, "Wrong password!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        registerReceiver(this.loginReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(this.loginReceiver);
        super.onDestroy();
    }
}
