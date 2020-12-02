package com.sonu.dagger2mvvm.di;

import androidx.lifecycle.ViewModelProvider;

import com.sonu.dagger2mvvm.viemodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory factory);
}
