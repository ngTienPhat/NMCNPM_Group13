package group13.ntphat.evernote.ui.workchat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkchatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WorkchatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is workchat fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}