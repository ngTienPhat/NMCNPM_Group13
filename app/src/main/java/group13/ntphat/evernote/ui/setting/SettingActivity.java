package group13.ntphat.evernote.ui.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.R;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);
        setTitle("Setting");
    }

    @Override
    public void onBackPressed() {
        MainActivity.navController.navigate(MainActivity.lastFragment);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MainActivity.navController.navigate(MainActivity.lastFragment);
        super.onBackPressed();
        return true;
    }
}
