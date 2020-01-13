package group13.ntphat.evernote;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.io.InputStream;
import java.net.URL;

import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.ui.notebook.NewNotebookDialog;
import group13.ntphat.evernote.ui.notes.NotesFragment;
import group13.ntphat.evernote.ui.notes.ViewNoteActivity;
import group13.ntphat.evernote.ui.setting.AccountInfoActivity;
import group13.ntphat.evernote.ui.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
, NewNotebookDialog.NewNotebookDialogListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BroadcastReceiver killReceiver;
    private BroadcastReceiver changeInfoReceiver;
    private BroadcastReceiver uploadAvatarReceiver;

    private TextView txtFullname;
    private TextView txtEmail;
    private ImageView imgAvatar;
    private ImageView editAccount;

    private int NOTE_CREATE_ACTIVITY_RESULT = 2;

    public static int lastFragment;
    public static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.addKillReceiver();
        this.addChangeInfoReceiver();
        this.addUploadAvatarReceiver();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupMenu(view);
            }
        });

        this.drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        this.navigationView.getMenu().getItem(0).setChecked(true);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_allnotes, R.id.nav_notebook, R.id.nav_tags, R.id.nav_photos,
                R.id.nav_workchat, R.id.nav_setting, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        this.lastFragment = R.id.nav_allnotes;
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        this.setUpHeader();
        DATA.getAllInfo(this.getBaseContext(), USER.getInstance().getUserID());
    }

    // -----------------------------------------------------------------
    // set up navigation header
    private void setUpHeader() {
        View headerLayout = this.navigationView.getHeaderView(0);
        this.txtFullname = (TextView) headerLayout.findViewById(R.id.txt_fullname);
        this.txtEmail = (TextView) headerLayout.findViewById(R.id.txt_email);
        this.imgAvatar = (ImageView) headerLayout.findViewById(R.id.img_profile);
        this.editAccount = headerLayout.findViewById(R.id.icon_account_setting);
        this.txtFullname.setText(USER.getInstance().getFullName());
        this.txtEmail.setText(USER.getInstance().getUserEmail());
        this.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettingAccount();
            }
        });

        this.editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettingAccount();
            }
        });

        USER.getInstance().updateAvatar(this, USER.getInstance().getAvatar());
    }

    // -----------------------------------------------------------------
    // open setting activity
    private void goToSettingAccount(){
        Intent intent = new Intent(MainActivity.this, AccountInfoActivity.class);
        MainActivity.this.startActivity(intent);
        drawer.closeDrawer(GravityCompat.START);
    }

    // -----------------------------------------------------------------
    // navigation item clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_setting) {
            Intent intent;
            intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            this.drawer.closeDrawer(GravityCompat.START);
            return false;
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

    // --------------------------------------------------
    // handle floating menu clicked
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
                        intent = new Intent(MainActivity.this, ViewNoteActivity.class);
                        intent.putExtra("noteid", "-1");
                        startActivityForResult(intent, NOTE_CREATE_ACTIVITY_RESULT);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTE_CREATE_ACTIVITY_RESULT) {
            //if (resultCode == Activity.RESULT_OK) {
                //NotesFragment.updateListNotes();
                MainActivity.loadLastFragment();
            //}
        }
    }

    // --------------------------------------------------
    // add Notebook dialog
    @Override
    public void applyTexts(String notebookName) throws JSONException {
        USER.getInstance().addNoteBook(this.getBaseContext(), notebookName);
    }

    public static void loadLastFragment() {
        navController.navigate(lastFragment);
    }

    private void addKillReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLOSE_ALL");
        this.killReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MainActivity.this.finish();
            }
        };
        registerReceiver(this.killReceiver, intentFilter);
    }

    private void addChangeInfoReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DATA");
        this.changeInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String name = intent.getStringExtra("name");
                int success = intent.getIntExtra("success", 0);
                if (name.equals("updateInfo")) {
                    if (success == 1) {
                        String fullname = USER.getInstance().getFullName();
                        if (fullname.compareTo(txtFullname.getText().toString()) != 0) {
                            txtFullname.setText(fullname);
                        }
                        String email = USER.getInstance().getUserEmail();
                        if (email.compareTo(txtEmail.getText().toString()) != 0) {
                            txtEmail.setText(email);
                        }
                        imgAvatar.setImageDrawable(USER.getInstance().getImgAvatar());
                    }
                }
            }
        };
        registerReceiver(this.changeInfoReceiver, intentFilter);
    }

    private void addUploadAvatarReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("AVATAR_UPLOADED");
        this.uploadAvatarReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String url = intent.getStringExtra("url");
                USER.getInstance().updateAvatar(context, url);
            }
        };
        registerReceiver(this.uploadAvatarReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(this.killReceiver);
        this.unregisterReceiver(this.changeInfoReceiver);
        this.unregisterReceiver(this.uploadAvatarReceiver);
        super.onDestroy();
    }

    public void setting(MenuItem item) {
        goToSettingAccount();
    }
}