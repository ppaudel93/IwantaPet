package project.com.iwantapet;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText memailfield,mpasswordfield;
    Button signinbutton;
    public static String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        registerViews();
        Button register = (Button) findViewById(R.id.registerbutton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoregister();
            }
        });
    }
    public void gotoregister(){
        Intent intent = new Intent(LoginScreen.this,RegisterActivity.class);
        startActivity(intent);
    }
    private void registerViews() {
        mpasswordfield = (EditText) findViewById(R.id.password_Login);
        // TextWatcher would let us check validation error on the fly
        mpasswordfield.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(mpasswordfield);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        memailfield = (EditText) findViewById(R.id.email_Login);
        memailfield.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(memailfield, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        signinbutton = (Button) findViewById(R.id.signinbutton);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if ( checkValidation () )
                    signIn();
                else
                    Toast.makeText(LoginScreen.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(mpasswordfield)) ret = false;
        if (!Validation.hasText(memailfield)) ret = false;
        if (!Validation.isEmailAddress(memailfield, true)) ret = false;
        return ret;
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void signIn(){
        //String email,password;
        EditText memailfield,mpasswordfield;
        memailfield=(EditText) findViewById(R.id.email_Login);
        mpasswordfield=(EditText) findViewById(R.id.password_Login);
        email=memailfield.getText().toString();
        password=mpasswordfield.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log .w(TAG,"SignInSuccessful",task.getException());
                                Toast.makeText(LoginScreen.this,R.string.auth_success,Toast.LENGTH_SHORT).show();
                                final Intent intent = new Intent(LoginScreen.this,MainScreen.class);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            }

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginScreen.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });


    }
}
