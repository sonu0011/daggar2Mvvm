package com.sonu.dagger2mvvm.di.main;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.sonu.dagger2mvvm.network.main.MainApi;
import com.sonu.dagger2mvvm.ui.main.posts.PostsRecyclerAdapter;
import com.sonu.dagger2mvvm.utill.VerticalSpacingItemDecoration;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostsRecyclerAdapter providePostsRecyclerAdapter() {
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static VerticalSpacingItemDecoration provideverticalSpacingItemDecoration() {
        return new VerticalSpacingItemDecoration(15);
    }

    @MainScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(Application application) {
        return new LinearLayoutManager(application);
    }

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
