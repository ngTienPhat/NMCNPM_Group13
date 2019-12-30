package group13.ntphat.evernote.ui.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import group13.ntphat.evernote.R;

public class NotebookFragment extends Fragment {

    private NotebookViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(NotebookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notebook, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        final ListView listView = root.findViewById(R.id.list_notebooks);
        ArrayList<NotebookItem> notebooks = new ArrayList<NotebookItem>();
        notebooks.add(new NotebookItem("Notebook1", 2));
        notebooks.add(new NotebookItem("Notebook2", 3));
        notebooks.add(new NotebookItem("Notebook3", 4));

        listView.setAdapter(new NotebookAdapter(this, notebooks));
        return root;
    }
}