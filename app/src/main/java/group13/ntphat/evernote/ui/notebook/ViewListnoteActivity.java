package group13.ntphat.evernote.ui.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import group13.ntphat.evernote.Model.NOTE;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.notes.NoteAdapter;
import group13.ntphat.evernote.ui.notes.NotesFragment;
import group13.ntphat.evernote.ui.notes.ViewNoteActivity;

public class ViewListnoteActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<NOTE> listNote;
    static private NoteAdapter listNoteAdapter;
    private String notebookId;
    private String tagId;
    private String notebookName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notes);

        Intent intentCatcher = getIntent();
        getKeyToGetListNote(intentCatcher);
        loadListNoteByKey(notebookId);
        notebookName = USER.getInstance().getNoteBook(notebookId).getNameNoteBook();
        setTitle(notebookName);

        listView = findViewById(R.id.list_notes);
        listNoteAdapter = new NoteAdapter(getApplicationContext(), R.layout.note_item, listNote);
        listView.setAdapter(listNoteAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent;
                intent = new Intent(getApplicationContext(), ViewCommonNoteActivity.class);
                NOTE chosenNote = listNote.get(position);

                putNoteToViewNoteActivity(intent, chosenNote);
                startActivity(intent);
            }
        });
        //openNoteFragment(notebookId);
    }

    private void getKeyToGetListNote(Intent intentCatcher){
        notebookId = intentCatcher.getStringExtra("notebookid");
    }

    private void loadListNoteByKey(String key){
        listNote = USER.getInstance().getNoteBook(key).notes;
    }

    private void openNoteFragment(String notebookId){
        Bundle args = new Bundle();
        args.putString("notebookid", notebookId);
        NotesFragment notesFragment = new NotesFragment();
        notesFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, notesFragment);
        fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
    private void putNoteToViewNoteActivity(Intent intent, NOTE note){
//        intent.putExtra("Notebook_name", USER.getInstance().getNoteBook(note.getNotebookID()).getNameNoteBook());
//        intent.putExtra("content", note.getContent());
        intent.putExtra("noteid", note.getNoteID());
        intent.putExtra("notebookid", note.getNotebookID());
    }

}
