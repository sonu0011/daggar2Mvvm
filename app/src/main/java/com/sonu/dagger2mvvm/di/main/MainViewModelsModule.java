package com.sonu.dagger2mvvm.di.main;

import androidx.lifecycle.ViewModel;

import com.sonu.dagger2mvvm.di.ViewModelKey;
import com.sonu.dagger2mvvm.ui.main.posts.PostsViewModel;
import com.sonu.dagger2mvvm.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    abstract ViewModel bindPostsViewModel(PostsViewModel postViewModel);
}
