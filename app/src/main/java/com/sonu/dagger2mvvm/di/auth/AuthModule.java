package com.sonu.dagger2mvvm.di.auth;

import com.sonu.dagger2mvvm.network.auth.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    AuthApi getApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }
}
