package com.nyit.employee_scheduler.data.model;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nyit.employee_scheduler.R;
import com.nyit.employee_scheduler.data.LoginRepository;
import com.nyit.employee_scheduler.data.Result;
import com.nyit.employee_scheduler.ui.login.LoggedInUserView;
import com.nyit.employee_scheduler.ui.login.LoginFormState;
import com.nyit.employee_scheduler.ui.login.LoginResult;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    MutableLiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    MutableLiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            /*loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                not sure when this is going to be used for, but I'm currently having trouble launching the app if I have this statement
             */
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}