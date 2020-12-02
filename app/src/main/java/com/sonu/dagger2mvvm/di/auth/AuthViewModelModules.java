package com.sonu.dagger2mvvm.di.auth;

import androidx.lifecycle.ViewModel;

import com.sonu.dagger2mvvm.di.ViewModelKey;
import com.sonu.dagger2mvvm.ui.auth.AuthVieModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModules {

    @Binds
    @IntoMap
    @ViewModelKey(AuthVieModel.class)
    public abstract ViewModel bindAuthViewModel(AuthVieModel authVieModel);
}
