package com.sonu.dagger2mvvm.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sonu.dagger2mvvm.SessionManager;
import com.sonu.dagger2mvvm.models.User;
import com.sonu.dagger2mvvm.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthVieModel extends ViewModel {
    private static final String TAG = "AuthVieModel";

    private AuthApi authApi;
    private SessionManager sessionManager;

    @Inject
    public AuthVieModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    public void authenticateUserWithId(int userId) {
        sessionManager.authenticateUserWithId(queryUserId(userId));
    }

    public LiveData<AuthResource<User>> queryUserId(int userId) {

        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, User>() {
                            @NonNull
                            @Override
                            public User apply(@NonNull Throwable throwable) throws Exception {
                                User user = new User();
                                user.setId(-1);
                                return user;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @NonNull
                            @Override
                            public AuthResource<User> apply(@NonNull User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResource.error("Could not authenticate", null);
                                }
                                return AuthResource.authenticated(user);

                            }
                        })
                        .subscribeOn(Schedulers.io())
        );

    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
