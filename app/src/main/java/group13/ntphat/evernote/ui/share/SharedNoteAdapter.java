package group13.ntphat.evernote.ui.share;

import android.content.Context;
import android.text.Layout;
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

import group13.ntphat.evernote.Model.NOTE;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.notes.NoteAdapter;

public class SharedNoteAdapter extends ArrayAdapter<NOTE>{
    private Context mContext;
    private ArrayList<NOTE> listSharedNotes;
    private int resource;

    public SharedNoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NOTE> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.listSharedNotes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
            convertView = layoutInflater.inflate(resource, parent, false);
        }
        TextView createDate = convertView.findViewById(R.id.note_date);
        TextView noteTitle = convertView.findViewById(R.id.note_title);
        TextView noteSharedUserName = convertView.findViewById(R.id.name_shared_user);
        ImageView noteSharedUserAvater = convertView.findViewById(R.id.user_avatar);

        NOTE note = listSharedNotes.get(position);
        createDate.setText(note.getCreateDate());
        noteTitle.setText(note.getTitle());

        return convertView;
    }
}