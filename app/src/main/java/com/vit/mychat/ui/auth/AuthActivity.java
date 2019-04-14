package com.vit.mychat.ui.auth;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.auth.AuthViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserViewModel;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.MainActivity;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.profile.ProfileActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class AuthActivity extends BaseActivity {
    private static final int RC_GOOGLE_SIGN_IN = 001;

    public static void moveAuthActivity(Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @BindView(R.id.layout_root)
    LinearLayout mLayoutRoot;

    @BindView(R.id.image_messenger)
    ImageView mImageMessenger;

    @BindView(R.id.input_email)
    EditText mInputEmail;

    @BindView(R.id.input_password)
    EditText mInputPassword;

    @BindView(R.id.input_password_confirm)
    EditText mInputPasswordConfirm;

    @BindView(R.id.view_line)
    View mViewLine;

    @BindView(R.id.button_login)
    Button mButtonLogin;

    @BindView(R.id.button_register)
    Button mButtonRegister;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private boolean isRegister = false;

    private AuthViewModel authViewModel;
    private UpdateUserViewModel updateUserViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        mAuth = FirebaseAuth.getInstance();
        initGoogleSignIn();

        GlideApp.with(this)
                .load(R.drawable.ic_messenger)
                .into(mImageMessenger);
        setKeyboardVisibilityListener();

        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel.class);
        updateUserViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpdateUserViewModel.class);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isRegister) {
            isRegister = false;
            switchRegisterUi(isRegister);
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.button_login)
    void onClickLogin() {
        String email = mInputEmail.getText().toString();
        String password = mInputPassword.getText().toString();

        authViewModel.login(email, password)
                .observe(this, resource -> {
                    switch (resource.getStatus()) {
                        case LOADING:
                            showHUD();
                            break;

                        case SUCCESS:
                            dismissHUD();
                            MainActivity.moveMainActivity(this);
                            finish();
                            break;

                        case ERROR:
                            dismissHUD();
                            showToast(resource.getThrowable().getMessage());
                            break;
                    }
                });
    }

    @OnClick(R.id.button_register)
    void onClickRegister() {
        if (!isRegister) {
            isRegister = true;
            switchRegisterUi(isRegister);
        } else {
            String email = mInputEmail.getText().toString();
            String password = mInputPassword.getText().toString();

            authViewModel.register(email, password)
                    .observe(this, resource -> {
                        switch (resource.getStatus()) {
                            case LOADING:
                                showHUD();
                                break;

                            case SUCCESS:
                                dismissHUD();
                                updateUserViewModel.updateUser(new UserViewData(authViewModel.getCurrentUserId(),
                                        getString(R.string.ten_cua_ban), getString(R.string.status_cua_ban),
                                        "", "", "", System.currentTimeMillis()));

                                ProfileActivity.moveProfileActivity(this, authViewModel.getCurrentUserId());
                                finish();
                                break;

                            case ERROR:
                                dismissHUD();
                                showToast(resource.getThrowable().getMessage());
                                break;
                        }
                    });
        }

    }

    @OnClick(R.id.image_google)
    void onClickGoogle() {
        signInGoogle();
    }

    @OnClick(R.id.image_facebook)
    void onClickFacebook() {

    }

    @OnFocusChange({R.id.input_password_confirm, R.id.input_password, R.id.input_email})
    void onFocusChange(boolean focused) {
        if (focused) mImageMessenger.setVisibility(View.GONE);
    }

    @OnClick({R.id.input_password_confirm, R.id.input_password, R.id.input_email})
    void onClick() {
        mImageMessenger.setVisibility(View.GONE);
    }

    private void switchRegisterUi(boolean isRegister) {
        mInputPasswordConfirm.setVisibility(isRegister ? View.VISIBLE : View.GONE);
        mViewLine.setVisibility(isRegister ? View.VISIBLE : View.GONE);
        mButtonLogin.setVisibility(isRegister ? View.GONE : View.VISIBLE);
        if (isRegister) {
            mButtonLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.right_to_left));
            mInputPasswordConfirm.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        } else {
            mInputPasswordConfirm.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            mButtonLogin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.left_to_right));
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);

                            boolean newuser = task.getResult().getAdditionalUserInfo().isNewUser();


                            if (newuser) {

                                //Do Stuffs for new user

                            } else {

                                //Continue with Sign fade_in
                            }

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void setKeyboardVisibilityListener() {
        KeyboardVisibilityEvent.setEventListener(
                this, isOpen -> {
                    if (!isOpen) mImageMessenger.setVisibility(View.VISIBLE);
                });
    }
}