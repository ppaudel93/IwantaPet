package project.com.iwantapet;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import static project.com.iwantapet.LoginScreen.email;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        String fname,lname;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        TextView emailinfo =(TextView)findViewById(R.id.email_mainpage);
        emailinfo.setText(email);
        ImageButton adoptbutton = (ImageButton)findViewById(R.id.adopt_mainpage);
        Button lnfbutton=(Button)findViewById(R.id.lnf_button);
        Button discuss=(Button)findViewById(R.id.discuss_button);
        discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent gotodiscuss = new Intent(MainScreen.this,DiscussionActivity.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(gotodiscuss);
                    }
                }, 500);
            }
        });
        adoptbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainScreen.this,AdoptFiller.class);
                startActivity(intent);
            }
        });
        lnfbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this,LnfActivity.class);
                startActivity(intent);
            }
        });
    }
}
