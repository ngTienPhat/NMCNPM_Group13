package group13.ntphat.evernote.ui.share;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import group13.ntphat.evernote.Model.NOTE;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.notes.NoteAdapter;

public class ShareFragment extends Fragment {

    private ListView listView;
    private ArrayList<NOTE> listNotes;
    private NoteAdapter listNoteAdapter;
    private int NOTE_ACTIVITY_RESULT = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        return root;
    }
}