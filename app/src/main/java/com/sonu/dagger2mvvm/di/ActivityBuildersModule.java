package com.sonu.dagger2mvvm.di;

import com.sonu.dagger2mvvm.di.auth.AuthModule;
import com.sonu.dagger2mvvm.di.auth.AuthViewModelModules;
import com.sonu.dagger2mvvm.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelModules.class,
                    AuthModule.class
            }
    )
    abstract AuthActivity authActivity();
}
