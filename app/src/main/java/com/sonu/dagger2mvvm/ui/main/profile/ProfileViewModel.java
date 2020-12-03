package com.sonu.dagger2mvvm.ui.main.profile;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sonu.dagger2mvvm.SessionManager;
import com.sonu.dagger2mvvm.models.User;
import com.sonu.dagger2mvvm.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";
    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager manager) {
        this.sessionManager = manager;
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }

}
