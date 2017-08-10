package project.com.iwantapet;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static project.com.iwantapet.DiscussionActivity.discpost;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText memail;
    private EditText mpassword;
    private EditText mfname;
    private EditText mlname;
    private Button submitbutton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        memail=(EditText) findViewById(R.id.email_register);
        mpassword=(EditText) findViewById(R.id.password_register);
        mfname=(EditText) findViewById(R.id.f_name_register);
        mlname=(EditText) findViewById(R.id.l_name_register);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signed in:",user.getUid());
                } else {
                    // User is signed out
                }
                // ...
            }
        };
        submitbutton=(Button)findViewById(R.id.submit_button);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createaccount();
            }
        });


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
    public void createaccount(){
        String email,password,fname,lname;
        mAuth=FirebaseAuth.getInstance();
        email=memail.getText().toString();
        password=mpassword.getText().toString();
        fname=mfname.getText().toString();
        lname=mlname.getText().toString();
        final DatabaseReference mD = FirebaseDatabase.getInstance().getReference("users");
        final String userid=mD.push().getKey();
        final Userinfo user1 = new Userinfo(fname,lname,email,userid);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, R.string.auth_success,
                                    Toast.LENGTH_SHORT).show();
                            mD.child(userid).setValue(user1);
                            final Intent intent = new Intent(RegisterActivity.this,LoginScreen.class);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        }
                        // If sign in fails, display a message to the user. If sign lin succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
