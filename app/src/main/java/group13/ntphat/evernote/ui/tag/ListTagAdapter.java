package group13.ntphat.evernote.ui.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import group13.ntphat.evernote.R;

public class ListTagAdapter extends BaseAdapter {
    private List<ListTagItem> items;
    private LayoutInflater inflater;

    public ListTagAdapter(LayoutInflater inflater, List<ListTagItem> items) {
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
            convertView = this.inflater.inflate(R.layout.list_tag_item, null);
            holder = new ViewHolder();
            holder.tagName = (TextView) convertView.findViewById(R.id.textView_tagName);
            holder.numberOfNotes = (TextView) convertView.findViewById(R.id.textView_numberOfNotes);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListTagItem item = this.items.get(position);
        holder.tagName.setText(item.getName());
        holder.numberOfNotes.setText(item.getNumberOfNotes() + " ghi chú");
        return convertView;
    }

    static class ViewHolder {
        TextView tagName;
        TextView numberOfNotes;
    }
}
