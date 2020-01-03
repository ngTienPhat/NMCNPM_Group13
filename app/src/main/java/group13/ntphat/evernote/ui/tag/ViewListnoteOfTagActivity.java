package group13.ntphat.evernote.ui.tag;

import android.content.Intent;

import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.ui.notebook.ViewListnoteActivity;

public class ViewListnoteOfTagActivity extends ViewListnoteActivity {
    @Override
    public void getKeyToGetListNote(Intent intentCatcher) {
        notebookId = intentCatcher.getStringExtra("tag");
    }

    @Override
    public void loadListNoteByKey(String key) {
        listNote = USER.getInstance().getNoteTag(key);
        setTitle(notebookId);
    }
}
