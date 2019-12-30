package group13.ntphat.evernote.ui.notebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import group13.ntphat.evernote.Model.NOTEBOOK;
import group13.ntphat.evernote.R;

public class ListNotebookAdapter extends BaseAdapter {
    private List<NOTEBOOK> items;
    private LayoutInflater inflater;

    public ListNotebookAdapter(LayoutInflater inflater, List<NOTEBOOK> items) {
        this.inflater = inflater;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.list_notebook_item, null);
            holder = new ViewHolder();
            holder.notebookName = (TextView) convertView.findViewById(R.id.textView_notebookName);
            holder.numberOfNotes = (TextView) convertView.findViewById(R.id.textView_numberOfNotes);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NOTEBOOK item = this.items.get(position);
        holder.notebookName.setText(item.getNameNoteBook());
        holder.numberOfNotes.setText(item.notes.size() + " ghi chú");
        return convertView;
    }

    static class ViewHolder {
        TextView notebookName;
        TextView numberOfNotes;
    }
}
