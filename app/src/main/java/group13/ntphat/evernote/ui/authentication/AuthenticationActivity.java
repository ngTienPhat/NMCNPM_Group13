package group13.ntphat.evernote.ui.authentication;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import group13.ntphat.evernote.R;

public class AuthenticationActivity extends FragmentActivity {

    final public static int FRAGMENT_LOGIN = 1;
    final public static int FRAGMENT_SIGN_UP = 2;

    private static FragmentManager myFragmentManager;
    private static LoginFragment loginFragment;
    private static SignUpFragment signUpFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

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
}
