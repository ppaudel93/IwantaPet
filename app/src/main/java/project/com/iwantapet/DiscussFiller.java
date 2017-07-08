package project.com.iwantapet;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static project.com.iwantapet.LoginScreen.email;

public class DiscussFiller extends AppCompatActivity {
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss_filler);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Button post = (Button)findViewById(R.id.discuss_post_button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postdiscussion();
            }
        });
    }
    private void postdiscussion(){
        String title,desc;
        TextView titletext,desctext;
        titletext=(TextView)findViewById(R.id.discuss_title);
        desctext=(TextView)findViewById(R.id.discuss_descr);
        title=titletext.getText().toString();
        desc=desctext.getText().toString();
        Postinfo post1=new Postinfo(title,desc,email);
        DatabaseReference mD=FirebaseDatabase.getInstance().getReference("discusspost");
        String discusspostid=mD.push().getKey();
        mD.child(discusspostid).setValue(post1);
        final Intent intent =new Intent(DiscussFiller.this,DiscussionActivity.class);
        Toast.makeText(DiscussFiller.this, R.string.post_success,
                Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
