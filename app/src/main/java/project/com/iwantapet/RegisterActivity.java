package project.com.iwantapet;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG ="EmailPassword" ;
    private DatabaseReference mDatabase;
    private TextView memail;
    private TextView mpassword;
    private TextView mfname;
    private TextView mlname;
    private Button submitbutton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        memail=(TextView)findViewById(R.id.email_register);
        mpassword=(TextView)findViewById(R.id.password_register);
        mfname=(TextView)findViewById(R.id.f_name_register);
        mlname=(TextView)findViewById(R.id.l_name_register);
        submitbutton=(Button)findViewById(R.id.submit_button);
        mAuth=FirebaseAuth.getInstance();
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createaccount();
            }
        });


    }
    public void createaccount(){
        String email,password,fname,lname;
        email=memail.getText().toString();
        password=mpassword.getText().toString();
        fname=mfname.getText().toString();
        lname=mlname.getText().toString();
        Userinfo user1 = new Userinfo(fname,lname,email);
        DatabaseReference mD = FirebaseDatabase.getInstance().getReference("users");
        String userid=mD.push().getKey();
        mD.child(userid).setValue(user1);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, R.string.auth_success,
                                    Toast.LENGTH_SHORT).show();
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
