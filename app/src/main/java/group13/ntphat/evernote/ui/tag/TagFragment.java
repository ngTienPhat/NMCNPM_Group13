package group13.ntphat.evernote.ui.tag;

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

public class TagFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag, container, false);
        final ListView listView = root.findViewById(R.id.list_tags);
        ArrayList<ListTagItem> tags = new ArrayList<ListTagItem>();
        tags.add(new ListTagItem("Tag 1", 2));
        tags.add(new ListTagItem("Tag 2", 3));
        tags.add(new ListTagItem("Tag 3", 4));

        listView.setAdapter(new ListTagAdapter(inflater, tags));
        return root;
    }
}