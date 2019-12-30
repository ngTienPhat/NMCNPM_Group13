package group13.ntphat.evernote.ui.notebook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotebookViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotebookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notebook fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}