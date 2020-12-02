package com.sonu.dagger2mvvm.network.auth;

import com.sonu.dagger2mvvm.models.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("users/{id}")
    Flowable<User> getUser(@Path("id") int id);
}
