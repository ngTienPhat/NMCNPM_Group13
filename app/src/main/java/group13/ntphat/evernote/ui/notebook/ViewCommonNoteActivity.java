package group13.ntphat.evernote.ui.notebook;

import android.app.Activity;
import android.content.Intent;

import group13.ntphat.evernote.ui.notes.ViewNoteActivity;

public class ViewCommonNoteActivity extends ViewNoteActivity {
    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("notebookid",getNotebookId());
        setResult(Activity.RESULT_OK,returnIntent);
        super.finish();
    }
}
