package com.sonu.dagger2mvvm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.sonu.dagger2mvvm.models.User;
import com.sonu.dagger2mvvm.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";

    @Inject
    public SessionManager() {
    }

    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    public void authenticateUserWithId(final LiveData<AuthResource<User>> source) {
        Log.d(TAG, "authenticateUserWithId:  cachedUser is" + cachedUser);
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading(null));
            cachedUser.addSource(source, userAuthResource -> {
                cachedUser.setValue(userAuthResource);
                cachedUser.removeSource(source);
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: ");
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
