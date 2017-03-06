package oktalkassignmentapp.sumit.com.oktalkassignmentapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import oktalkassignmentapp.sumit.com.oktalkassignmentapp.R;

public class OkTalkAssigmentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_talk_assigment_main);

        Button assignment1Btn = (Button) findViewById(R.id.run_assignment_1_btn);
        assignment1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OkTalkAssigmentMainActivity.this, Assignment1Activity.class);
                startActivity(intent);
            }
        });

        Button assignment2Btn = (Button) findViewById(R.id.run_assignment_2_btn);
        assignment2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OkTalkAssigmentMainActivity.this, "Sorry I have not completed this problem due to time constraint", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OkTalkAssigmentMainActivity.this, Assignment2Activity.class);
                startActivity(intent);
            }
        });
    }
}
