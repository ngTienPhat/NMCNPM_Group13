package group13.ntphat.evernote.ui.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import group13.ntphat.evernote.Model.NOTEBOOK;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;

public class NotebookFragment extends Fragment {
    private ListView listView;
    private ArrayList<NOTEBOOK> notebooks;

    static public ListNotebookAdapter listNotebookAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notebook, container, false);
        setHasOptionsMenu(true);

        listView = root.findViewById(R.id.list_notebooks);
        notebooks = USER.getInstance().getAllNoteBook();

        if (notebooks.size() != 0){
            listNotebookAdapter = new ListNotebookAdapter(inflater, notebooks);
            listView.setAdapter(listNotebookAdapter);
        }
        else {
            TextView textView = (TextView)root.findViewById(R.id.textView_whenEmpty);
            textView.setText("Không có sổ tay nào");
        }
        setListviewItemClicked();
        return root;
    }

    private void setListviewItemClicked(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getContext(), ViewListnoteActivity.class);
                intent.putExtra("notebookid", notebooks.get(position).getNotebookID());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notebook_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_create_notebook:
                openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDialog() {
        NewNotebookDialog newNotebookDialog = new NewNotebookDialog();
        newNotebookDialog.show(getFragmentManager(), "example dialog");
    }

}