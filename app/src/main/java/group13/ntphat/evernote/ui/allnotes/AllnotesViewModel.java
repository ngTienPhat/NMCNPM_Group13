package group13.ntphat.evernote.ui.allnotes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllnotesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllnotesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is all note fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}