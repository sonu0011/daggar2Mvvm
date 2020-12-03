package com.sonu.dagger2mvvm.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sonu.dagger2mvvm.R;
import com.sonu.dagger2mvvm.models.Post;
import com.sonu.dagger2mvvm.ui.main.Resource;
import com.sonu.dagger2mvvm.utill.VerticalSpacingItemDecoration;
import com.sonu.dagger2mvvm.viemodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment extends DaggerFragment {
    private static final String TAG = "PostFragment";
    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    VerticalSpacingItemDecoration itemDecoration;

    @Inject
    PostsRecyclerAdapter recyclerAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);

        viewModel = new ViewModelProvider(this, providerFactory).get(PostsViewModel.class);

        initRecyclerView();
        subscribeObervers();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void subscribeObervers() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {

                        case LOADING: {
                            Log.d(TAG, "onChanged: LOADING...");
                            break;
                        }

                        case SUCCESS: {
                            Log.d(TAG, "onChanged: got posts..." + listResource.data.toString());
                            recyclerAdapter.setPosts(listResource.data);
                            break;
                        }

                        case ERROR: {
                            Log.e(TAG, "onChanged: ERROR..." + listResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }
}
