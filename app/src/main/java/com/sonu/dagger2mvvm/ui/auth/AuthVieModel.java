package com.sonu.dagger2mvvm.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.sonu.dagger2mvvm.models.User;
import com.sonu.dagger2mvvm.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthVieModel extends ViewModel {
    private static final String TAG = "AuthVieModel";

    private AuthApi authApi;
    private MediatorLiveData<AuthResource<User>> userData = new MediatorLiveData<>();

    @Inject
    public AuthVieModel(AuthApi authApi) {
        this.authApi = authApi;
    }

    public void authenticateUserWithId(int userId) {
        userData.setValue(AuthResource.loading(null));
        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
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
        userData.addSource(source, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> data) {
                userData.setValue(data);
                userData.removeSource(source);
            }
        });
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return userData;
    }
}
