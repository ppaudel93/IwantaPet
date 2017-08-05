package project.com.iwantapet;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static project.com.iwantapet.DiscussionActivity.discpost;
import static project.com.iwantapet.LoginScreen.email;

public class DisplayDescriptionPost extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    EditText comment;
    Button okbutton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_description_post);
        myRef.keepSynced(true);
        TextView title=(TextView)findViewById(R.id.title_discview);
        TextView desc = (TextView)findViewById(R.id.description_discview);
        title.setText(discpost.title);
        desc.setText(discpost.description);
        myRef.child("discusspost").child(discpost.id).child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final CommentInfo comment1 = dataSnapshot.getValue(CommentInfo.class);
                LinearLayout main = (LinearLayout)findViewById(R.id.mainlinear);
                LinearLayout secondary=new LinearLayout(DisplayDescriptionPost.this);
                secondary.setOrientation(LinearLayout.VERTICAL);
                TextView comment = new TextView(DisplayDescriptionPost.this);
                TextView commenter = new TextView(DisplayDescriptionPost.this);
                comment.setTextSize(20);
                comment.setPadding(10,10,20,10);
                comment.setText(comment1.comment);
                commenter.setTextSize(10);
                commenter.setPadding(10,10,20,10);
                commenter.setText(comment1.commenter);
                secondary.addView(comment);
                secondary.addView(commenter);
                int red = Color.parseColor("#ff3333");
                secondary.setBackgroundColor(red);
                LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lay.setMargins(5,10,0,10);
                main.addView(secondary,lay);

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
        mDatabase= FirebaseDatabase.getInstance().getReference();
        checkcomment();

    }
    private void checkcomment(){
        comment=(EditText)findViewById(R.id.comment);
        comment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(comment);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        okbutton=(Button)findViewById(R.id.ok);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation())
                    postcomment();
                else
                    Toast.makeText(DisplayDescriptionPost.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean checkValidation(){
        boolean ret=true;
        if (!Validation.hasText(comment)) ret = false;
        return ret;
    }
    public void postcomment(){
        String comment,poster,id;
        EditText com= (EditText)findViewById(R.id.comment);
        comment = com.getText().toString();
        poster=email;
        DatabaseReference mD = FirebaseDatabase.getInstance().getReference("discusspost").child(discpost.id).child("comments");
        Log.d("here's the id", discpost.id);
        id=mD.push().getKey();
        CommentInfo comment1 = new CommentInfo(comment,poster,id);
        mD.child(id).setValue(comment1);
        Toast.makeText(DisplayDescriptionPost.this, R.string.post_success,
                Toast.LENGTH_SHORT).show();

    }

    }

