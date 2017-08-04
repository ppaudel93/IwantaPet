package project.com.iwantapet;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import static project.com.iwantapet.LoginScreen.email;
import static project.com.iwantapet.AdoptFiller.id;

public class MainScreen extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    static Adoptinfo statpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        myRef.keepSynced(true);
        String fname,lname;
        TextView emailinfo =(TextView)findViewById(R.id.email_mainpage);
        emailinfo.setText(email);
        ImageButton adoptbutton = (ImageButton)findViewById(R.id.adopt_mainpage);
        LinearLayout postlayout = (LinearLayout)findViewById(R.id.postslist);
        myRef.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildAdded", dataSnapshot.toString());
                final Adoptinfo post1 = dataSnapshot.getValue(Adoptinfo.class);
                LinearLayout postlayout = (LinearLayout)findViewById(R.id.postslist);
                LinearLayout horizon = new LinearLayout(MainScreen.this);
                horizon.setOrientation(LinearLayout.HORIZONTAL);
                TextView pettype = new TextView(MainScreen.this);
                pettype.setTextSize(20);
                pettype.setPadding(10,10,20,10);
                pettype.setText(post1.type + " - ");
                TextView petemail = new TextView(MainScreen.this);
                petemail.setTextSize(20);
                petemail.setPadding(10,10,20,10);
                petemail.setText(post1.pemail);
                horizon.addView(pettype);
                horizon.addView(petemail);
                horizon.isClickable();
                horizon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainScreen.this,PostDetails.class);
                        statpost=post1;
                        startActivity(intent);
                    }
                });
                postlayout.addView(horizon);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildChanged", dataSnapshot.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("onChildRemoved", dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildMoved", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled", databaseError.getMessage().toString());
            }
        });
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
                final Intent intent= new Intent(MainScreen.this,AdoptFiller.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 500);
            }
        });
        lnfbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(MainScreen.this,LnfActivity.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 500);
            }
        });
    }
}
