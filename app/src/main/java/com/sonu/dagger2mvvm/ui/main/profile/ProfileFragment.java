package com.sonu.dagger2mvvm.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sonu.dagger2mvvm.R;
import com.sonu.dagger2mvvm.models.User;
import com.sonu.dagger2mvvm.ui.auth.AuthResource;
import com.sonu.dagger2mvvm.ui.auth.AuthVieModel;
import com.sonu.dagger2mvvm.viemodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";
    private TextView email, username, website;

    @Inject
    ViewModelProviderFactory providerFactory;

    private ProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        viewModel = new ViewModelProvider(this, providerFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }
    private void subscribeObservers(){
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), userAuthResource -> {
            if(userAuthResource != null){
                switch (userAuthResource.status){

                    case AUTHENTICATED:{
                        Log.d(TAG, "onChanged: ProfileFragment: AUTHENTICATED... " +
                                "Authenticated as: " + userAuthResource.data.getEmail());
                        setUserDetails(userAuthResource.data);
                        break;
                    }

                    case ERROR:{
                        Log.d(TAG, "onChanged: ProfileFragment: ERROR...");
                        setErrorDetails(userAuthResource.message);
                        break;
                    }
                }
            }
        });
    }

    private void setErrorDetails(String message){
        email.setText(message);
        username.setText("error");
        website.setText("error");
    }

    private void setUserDetails(User user){
        email.setText(user.getEmail());
        username.setText(user.getUsername());
        website.setText(user.getWebsite());
    }

}
