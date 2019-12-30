package group13.ntphat.evernote.ui.allnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import group13.ntphat.evernote.R;

public class NoteAdapter extends ArrayAdapter<NOTE> {
    private Context mContext;
    private ArrayList<NOTE> listNotes;
    int resource;

    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NOTE> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listNotes = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
            convertView = layoutInflater.inflate(resource, parent, false);
        }

        TextView noteTitle = convertView.findViewById(R.id.note_title);
        TextView noteDate = convertView.findViewById(R.id.note_date);
        ImageView noteImg = convertView.findViewById(R.id.note_img);
        TextView noteContent = convertView.findViewById(R.id.note_content);

        return convertView;
    }

}
