package group13.ntphat.evernote.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.Model.NOTE;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;

public class NotesFragment extends Fragment {
    private ListView listView;
    private ArrayList<NOTE> listNote;
    static public NoteAdapter listNoteAdapter;
    private View root;
    private boolean isHasListnote;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        isHasListnote=false;
        root = inflater.inflate(R.layout.fragment_notes, container, false);
        loadListNotes();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent;
                intent = new Intent(getContext(),ViewNoteActivity.class);
                NOTE chosenNote = listNote.get(position);

                putNoteToViewNoteActivity(intent, chosenNote);
                startActivity(intent);
            }
        });

        return root;
    }

    static public void updateListNotes(){
        listNoteAdapter.notifyDataSetChanged();
        MainActivity.navController.navigate(MainActivity.lastFragment);
    }

    private void getListNoteIfExist(){
        String passedNotebookId = "";
        isHasListnote=false;
        try{
            passedNotebookId = getArguments().getParcelable("notebookid");
        } catch (Exception e) {
            return;
        }
        listNote = USER.getInstance().getNoteBook(passedNotebookId).notes;
        isHasListnote = true;
    }

    private void loadListNotes(){
        getListNoteIfExist();
        if (!isHasListnote)
            initListNote();
        listNoteAdapter = new NoteAdapter(getContext(), R.layout.note_item, listNote);
        listView = root.findViewById(R.id.list_notes);
        listView.setAdapter(listNoteAdapter);
    }

    private void initListNote(){
        listNote = USER.getInstance().getAllNote();
    }

    private void putNoteToViewNoteActivity(Intent intent, NOTE note){
//        intent.putExtra("Notebook_name", USER.getInstance().getNoteBook(note.getNotebookID()).getNameNoteBook());
//        intent.putExtra("content", note.getContent());
        intent.putExtra("noteid", note.getNoteID());
        intent.putExtra("notebookid", note.getNotebookID());
    }
}