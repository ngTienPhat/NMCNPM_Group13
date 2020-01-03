package group13.ntphat.evernote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.ui.notebook.NewNotebookDialog;
import group13.ntphat.evernote.ui.notes.ViewNoteActivity;
import group13.ntphat.evernote.ui.setting.SettingActivity;
import xute.markdeditor.EditorControlBar;
import xute.markdeditor.MarkDEditor;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
, NewNotebookDialog.NewNotebookDialogListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    public static int lastFragment;
    public static NavController navController;

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            int success = intent.getIntExtra("success", 0);

            if (name.equals("login")) {
                if (success == 1) {
                    DATA.getAllInfo(context, USER.getInstance().getUserID());
                }else {

                }
            }

            if (name.equals("update")) {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /////////////////////////////////////////////////////////////////
        //setContentView(R.layout.main_login);



        /////////////////////////////////////////////////////////////////
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupMenu(view);
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DATA");
        registerReceiver(new MyReceiver(), intentFilter);
//        DATA.login(this.getBaseContext(), "user003", "123456");
        DATA.getAllInfo(this.getBaseContext(), "c3acc55a2d1411eab30000d86105dafc");
        this.drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_allnotes, R.id.nav_notebook, R.id.nav_tags, R.id.nav_photos,
                R.id.nav_workchat, R.id.nav_setting, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        this.lastFragment = R.id.nav_allnotes;
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getItemId() == R.id.nav_setting) {
            Intent intent;
            intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else {
            lastFragment = item.getItemId();
            NavigationUI.onNavDestinationSelected(item, this.navController);
        }
        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void createPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.floating_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add_reminder:
                        Toast.makeText(MainActivity.this, "Reminder!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_add_record:
                        Toast.makeText(MainActivity.this, "Record!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_add_attachment:
                        Toast.makeText(MainActivity.this, "Attachment!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_add_outline:
                        Toast.makeText(MainActivity.this, "Outline!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_add_picture:
                        Toast.makeText(MainActivity.this, "Picture!", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_add_note:
                        Intent intent;
                        intent = new Intent(getApplicationContext(), ViewNoteActivity.class);
                        intent.putExtra("noteid", "-1");
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    @Override
    public void applyTexts(String notebookName) throws JSONException {
        USER.getInstance().addNoteBook(this.getBaseContext(), notebookName);
    }
}