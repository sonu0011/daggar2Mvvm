package com.sonu.dagger2mvvm.di.main;

import com.sonu.dagger2mvvm.ui.main.posts.PostFragment;
import com.sonu.dagger2mvvm.ui.main.posts.PostsViewModel;
import com.sonu.dagger2mvvm.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentsBuildersModule {
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contributePostsFragment();

}
