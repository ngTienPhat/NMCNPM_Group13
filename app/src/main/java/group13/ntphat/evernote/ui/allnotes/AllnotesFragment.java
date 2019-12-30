package group13.ntphat.evernote.ui.allnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import group13.ntphat.evernote.R;

public class AllnotesFragment extends Fragment {

    private AllnotesViewModel homeViewModel;
    private ListView listView;
    private ArrayList<NOTE> listNote;
    private NoteAdapter listNoteAdapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(AllnotesViewModel.class);
        root = inflater.inflate(R.layout.fragment_allnotes, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//        loadListNotes();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent;
//                intent = new Intent(getContext(),ViewNoteActivity.class);
//                NOTE chosenNote = listNote.get(position);
//
//                putNoteToViewNoteActivity(intent, chosenNote);
//                startActivity(intent);
//            }
//        });
        return root;
    }

    private void loadListNotes(){
        listNoteAdapter = new NoteAdapter(getContext(), R.layout.note_item, listNote);
        listView = root.findViewById(R.id.list_notes);
        listView.setAdapter(listNoteAdapter);
    }

    private void initListNote(){

    }

    private void putNoteToViewNoteActivity(Intent intent, NOTE note){

    }

}