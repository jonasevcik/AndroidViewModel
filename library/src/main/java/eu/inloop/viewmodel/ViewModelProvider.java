package eu.inloop.viewmodel;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.SimpleArrayMap;

/**
 * Create and keep this class inside your Activity. Store it
 * in {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()
 * and restore in {@link android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)} before
 * calling the super implemenentation.
 */
public class ViewModelProvider {

    private final SimpleArrayMap<String, BaseViewModel<? extends IView>> mViewModelCache;

    public static ViewModelProvider newInstance(@NonNull final FragmentActivity activity) {
        if (activity.getLastCustomNonConfigurationInstance() == null) {
            return new ViewModelProvider();
        } else {
            return  (ViewModelProvider) activity.getLastCustomNonConfigurationInstance();
        }
    }

    @SuppressWarnings({"deprecation", "unused"})
    @Deprecated
    public static ViewModelProvider newInstance(@NonNull final Activity activity) {
        if (activity.getLastNonConfigurationInstance() == null) {
            return new ViewModelProvider();
        } else {
            return  (ViewModelProvider) activity.getLastNonConfigurationInstance();
        }
    }

    private ViewModelProvider() {
        mViewModelCache = new SimpleArrayMap<>();
    }

    public synchronized void remove(String modeIdentifier) {
        mViewModelCache.remove(modeIdentifier);
    }

    public synchronized void removeAllViewModels() {
        mViewModelCache.clear();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public synchronized <T extends IView> ViewModelWrapper<T> getViewModel(final String modelIdentifier,
                                                                           final @NonNull Class<? extends BaseViewModel<T>> viewModelClass) {
        BaseViewModel<T> instance = (BaseViewModel<T>) mViewModelCache.get(modelIdentifier);
        if (instance != null) {
            return new ViewModelWrapper<>(instance, false);
        }

        try {
            instance = viewModelClass.newInstance();
            instance.setUniqueIdentifier(modelIdentifier);
            mViewModelCache.put(modelIdentifier, instance);
            return new ViewModelWrapper<>(instance, true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class ViewModelWrapper<T extends IView> {
        @NonNull
        public final BaseViewModel<T> viewModel;
        public final boolean wasCreated;

        private ViewModelWrapper(@NonNull BaseViewModel<T> mViewModel, boolean mWasCreated) {
            this.viewModel = mViewModel;
            this.wasCreated = mWasCreated;
        }
    }
}
