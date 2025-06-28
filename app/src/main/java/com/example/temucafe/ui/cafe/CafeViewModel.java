package com.example.temucafe.ui.cafe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CafeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CafeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
