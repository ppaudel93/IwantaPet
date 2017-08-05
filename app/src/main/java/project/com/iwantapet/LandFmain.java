package project.com.iwantapet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LandFmain extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    static LnfPostInfo lnfpoststat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_fmain);
        myRef.keepSynced(true);
        Button lnfpost= (Button)findViewById(R.id.lnfpost);
        lnfpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandFmain.this,LnfActivity.class);
                startActivity(intent);
            }
        });
        myRef.keepSynced(true);
        myRef.child("lnf").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildAdded", dataSnapshot.toString());
                final LnfPostInfo lfp=dataSnapshot.getValue(LnfPostInfo.class);
                LinearLayout mainlayout = (LinearLayout)findViewById(R.id.lnfvertical);
                LinearLayout sublayout = new LinearLayout(LandFmain.this);
                sublayout.setOrientation(LinearLayout.VERTICAL);
                TextView maintext = new TextView(LandFmain.this);
                maintext.setTextSize(20);
                maintext.setText(lfp.status+" - "+lfp.name+" - "+lfp.type);
                int lblue = Color.parseColor("#66ccff");
                sublayout.setBackgroundColor(lblue);
                LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lay.setMargins(5,5,5,5);
                sublayout.addView(maintext);
                sublayout.isClickable();
                sublayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LandFmain.this,LnfDisplayer.class);
                        lnfpoststat=lfp;
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


    }
}
