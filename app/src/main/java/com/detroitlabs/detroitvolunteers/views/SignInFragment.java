package com.detroitlabs.detroitvolunteers.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.detroitlabs.detroitvolunteers.R;
import com.detroitlabs.detroitvolunteers.firebase.UserAuthCallBack;
import com.detroitlabs.detroitvolunteers.firebase.UserAuthentication;
import com.detroitlabs.detroitvolunteers.firebase.VolunteerMatchFirebase;
import com.detroitlabs.detroitvolunteers.models.User;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class SignInFragment extends RoboFragment implements UserAuthCallBack {


    @InjectView(R.id.edittext_username)
    EditText usernameField;

    @InjectView(R.id.edittext_email)
    EditText userEmailField;

    @InjectView(R.id.edittext_username)
    EditText passwordField;

    @InjectView(R.id.button_login)
    Button loginButton;

    @InjectView(R.id.text_signup)
    TextView signUpLink;

    UserAuthentication userAuthentication = new UserAuthentication(new VolunteerMatchFirebase());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserEmailFieldVisible()) {
                    if (signUpFieldsComplete()) {
                        signUpNewUser(usernameField.getText().toString(),
                                      userEmailField.getText().toString(),
                                      passwordField.getText().toString());
                    }
                }
                else{
                    if(logInFieldsComplete()){
                        logInReturningUser(usernameField.getText().toString(),
                                           passwordField.getText().toString());
                    }
                }
            }
        });
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSignUpView();
            }
        });
    }

    private void updateSignUpView(){
        if(!isUserEmailFieldVisible()){
            userEmailField.setVisibility(View.VISIBLE);
            loginButton.setText(R.string.button_sign_up);
        }
        else {
            userEmailField.setVisibility(View.GONE);
            loginButton.setText(R.string.button_login);
        }
    }

    private boolean isUserEmailFieldVisible(){
        return userEmailField.getVisibility() == View.VISIBLE;
    }

    private boolean signUpFieldsComplete(){
        return isTextNotEmpty(userEmailField) & logInFieldsComplete();
    }

    private boolean logInFieldsComplete(){
        return isTextNotEmpty(usernameField) && isTextNotEmpty(passwordField);
    }

    private boolean isTextNotEmpty(EditText editText){
        return !TextUtils.isEmpty(editText.getText());
    }

    private void signUpNewUser(String userName, String userEmail, String password){
        User user = new User.Builder(userName).withUserEmail(userEmail).build();
        userAuthentication.createNewUser(user, password, this);
    }

    private void logInReturningUser(String userName, String password){
        User user = new User.Builder(userName).build();
        userAuthentication.authenticateUser(user, password, this);
    }

    @Override
    public void onSuccess(User user) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, ListResultsFragment.newInstance(user))
                .commit();
    }

    @Override
    public void onError(String errorMessage) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }
}


