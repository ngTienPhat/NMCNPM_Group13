package group13.ntphat.evernote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.ui.notes.ViewNoteActivity;
import xute.markdeditor.EditorControlBar;
import xute.markdeditor.MarkDEditor;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MarkDEditor markDEditor;
    private EditorControlBar editorControlBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        DATA.getAllInfo(this.getBaseContext(), "c3acc55a2d1411eab30000d86105dafc");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_allnotes, R.id.nav_notebook, R.id.nav_tags,
                R.id.nav_photos, R.id.nav_workchat, R.id.nav_setting, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
}