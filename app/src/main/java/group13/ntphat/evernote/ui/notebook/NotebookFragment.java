package group13.ntphat.evernote.ui.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        final ListView listView = root.findViewById(R.id.list_notebooks);
        ArrayList<ListNotebookItem> notebooks = new ArrayList<ListNotebookItem>();
//        notebooks.add(new ListNotebookItem("Notebook1", 2));
//        notebooks.add(new ListNotebookItem("Notebook2", 3));
//        notebooks.add(new ListNotebookItem("Notebook3", 4));

        listView.setAdapter(new ListNotebookAdapter(inflater, notebooks));
        return root;
    }
}