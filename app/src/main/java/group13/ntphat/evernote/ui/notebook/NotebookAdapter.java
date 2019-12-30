package group13.ntphat.evernote.ui.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import group13.ntphat.evernote.R;

public class NotebookAdapter extends BaseAdapter {
    private List<NotebookItem> items;
    private LayoutInflater inflater;
    private Fragment context;

    public NotebookAdapter(Fragment c, List<NotebookItem> items) {
        this.context = c;
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
            convertView = inflater.inflate(R.layout.list_notebook_item, null);
            holder = new ViewHolder();
            holder.notebookName = (TextView) convertView.findViewById(R.id.textView_notebookName);
            holder.numberOfNotes = (TextView) convertView.findViewById(R.id.textView_numberOfNotes);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NotebookItem item = this.items.get(position);
        holder.notebookName.setText(item.getName());
        holder.numberOfNotes.setText(item.getNumberOfNotes() + " ghi chú");
        return convertView;
    }

    static class ViewHolder {
        TextView notebookName;
        TextView numberOfNotes;
    }
}
