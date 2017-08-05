package project.com.iwantapet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static project.com.iwantapet.LoginScreen.email;

public class DiscussionActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    static Postinfo discpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        Button creatediscuss = (Button)findViewById(R.id.create_forum);
        myRef.keepSynced(true);
        myRef.child("discusspost").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Postinfo discusspost1= dataSnapshot.getValue(Postinfo.class);
                LinearLayout mainlayout = (LinearLayout)findViewById(R.id.postvertical);
                LinearLayout sublayout = new LinearLayout(DiscussionActivity.this);
                sublayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout subtitle = new LinearLayout(DiscussionActivity.this);
                LinearLayout subdesc = new LinearLayout(DiscussionActivity.this);
                subtitle.setOrientation(LinearLayout.VERTICAL);
                subdesc.setOrientation(LinearLayout.VERTICAL);
                TextView titletext = new TextView(DiscussionActivity.this);
                TextView desctext = new TextView(DiscussionActivity.this);
                titletext.setTextSize(25);
                desctext.setTextSize(15);
                Typeface boldtype =Typeface.defaultFromStyle(Typeface.BOLD);
                titletext.setTypeface(boldtype);
                titletext.setText("Title: "+discusspost1.title);
                desctext.setText("Description:"+discusspost1.description);
                int lblue = Color.parseColor("#66ccff");
                int lgreen = Color.parseColor("#66ffb3");
                subtitle.setBackgroundColor(lblue);
                subdesc.setBackgroundColor(lgreen);
                LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lay.setMargins(5,5,5,5);
                subtitle.addView(titletext);
                subdesc.addView(desctext);
                sublayout.addView(subtitle);
                sublayout.addView(subdesc);
                sublayout.isClickable();
                sublayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DiscussionActivity.this,DisplayDescriptionPost.class);
                        discpost=discusspost1;
                        startActivity(intent);
                    }
                });
                mainlayout.addView(sublayout,lay);




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        creatediscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodiscussfiller=new Intent(DiscussionActivity.this,DiscussFiller.class);
                startActivity(gotodiscussfiller);
            }
        });
    }
}
