package com.sonu.dagger2mvvm.ui.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.sonu.dagger2mvvm.R;
import com.sonu.dagger2mvvm.models.User;
import com.sonu.dagger2mvvm.ui.main.MainActivity;
import com.sonu.dagger2mvvm.viemodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

//if we are using ContributesAndroidInjector then we have to use DaggerAppCompactActivity
public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private AuthVieModel authVieModel;
    private EditText userInput;
    private ProgressBar progressBar;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager glide;

    @Inject
    Drawable logo;

    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userInput = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(this);
        setLogo();
        authVieModel = new ViewModelProvider(this, providerFactory).get(AuthVieModel.class);
        SubscribeObservers();

    }

    private void SubscribeObservers() {
        authVieModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {

                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }

                        case AUTHENTICATED: {
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            onLoginSuccess();

                            break;
                        }

                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message
                                    + "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLoginSuccess() {
        Log.d(TAG, "onLoginSuccess: login successful!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo() {
        glide.load(logo).into((ImageView) findViewById((R.id.login_logo)));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            attemptLogin();
        }

    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userInput.getText().toString())) {
            return;
        }
        authVieModel.authenticateUserWithId(Integer.parseInt(userInput.getText().toString()));
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
