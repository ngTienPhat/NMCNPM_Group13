package group13.ntphat.evernote.ui.allnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private NoteAdapter noteAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(AllnotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_allnotes, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void loadListNotes(){

    }

    private void initListNote(){

    }

    private void putNoteToViewNoteActivity(Intent intent, NOTE note){

    }

}