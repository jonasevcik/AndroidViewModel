package eu.inloop.viewmodel.sample.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import eu.inloop.viewmodel.BaseViewModel;
import eu.inloop.viewmodel.IView;

public class SampleArgumentViewModel extends BaseViewModel<IView> {

    public static final String ARG_INT_USER_ID = "ARG_INT_USER_ID";

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);

        Log.d("SampleArgumentViewModel", "ViewModel created with argument - " + arguments.getInt(ARG_INT_USER_ID));
        if (savedInstanceState != null) {
            Log.d("SampleArgumentViewModel", "Application killed by system, viewmodel is restored");
        }
    }
}
