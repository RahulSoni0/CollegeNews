package com.example.collegenews.ui.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegenews.databinding.ActivityAuthBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private static final int AUTHUI_REQUEST_CODE = 3000;
    ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        //

        binding.btnEmailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEmailLogin(view);
            }
        });
        binding.btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGoogleLogin(view);
            }
        });

        binding.btnPhoneNoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePhoneLogin(view);
            }
        });


    }

    private void handlePhoneLogin(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(

                new AuthUI.IdpConfig.PhoneBuilder().build());

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com").build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }

    private void handleGoogleLogin(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com").build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }

    private void handleEmailLogin(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com").build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult: " + user.toString());
                Toast.makeText(this, "Singin Successfull", Toast.LENGTH_SHORT).show();
                //Checking for User (New/Old)
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                    //This is a New User
                    Intent intent = new Intent(this, OnboardingActivity.class);
                    intent.putExtra("userKey", user);
                    startActivity(intent);
                    this.finish();
                    Toast.makeText(this, "Welcome new user", Toast.LENGTH_SHORT).show();
                } else {
                    //This is a returning user
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("userKey", user);
                    startActivity(intent);
                    this.finish();
                    Toast.makeText(this, "Welcome back ", Toast.LENGTH_SHORT).show();
                }


            } else {
                // Signing in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                    Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                } else {
                    Log.e(TAG, "onActivityResult: ", response.getError());
                }
            }
        }
    }
}