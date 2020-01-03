package group13.ntphat.evernote.ui.tag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.notebook.ViewListnoteActivity;

public class TagFragment extends Fragment {
    private ListView listView;
    private ArrayList<ListTagItem> tags;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag, container, false);
        listView = root.findViewById(R.id.list_tags);
        tags = getTags();
        if (tags.size() != 0){
            listView.setAdapter(new ListTagAdapter(inflater, tags));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(getContext(), ViewListnoteOfTagActivity.class);
                    intent.putExtra("tag", tags.get(position).getName());
                    startActivity(intent);
                }
            });
        }
        else {
            TextView textView = (TextView)root.findViewById(R.id.textView_whenEmpty);
            textView.setText("Không có thẻ nào");
        }
        return root;
    }

    private ArrayList<ListTagItem> getTags() {
        ArrayList<ListTagItem> tags = new ArrayList<ListTagItem>();
        Map<String, Integer> map = USER.getInstance().getAllTag();
        for (String name : map.keySet())
            tags.add(new ListTagItem(name, map.get(name)));
        return tags;
    }
}