package group13.ntphat.evernote.ui.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import group13.ntphat.evernote.Model.NOTEBOOK;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;

public class NotebookFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notebook, container, false);
        setHasOptionsMenu(true);

        final ListView listView = root.findViewById(R.id.list_notebooks);
        ArrayList<NOTEBOOK> notebooks = USER.getInstance().getAllNoteBook();

        if (notebooks.size() != 0)
            listView.setAdapter(new ListNotebookAdapter(inflater, notebooks));
        else {
            TextView textView = (TextView)root.findViewById(R.id.textView_whenEmpty);
            textView.setText("Không có sổ tay nào");
        }
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notebook_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}