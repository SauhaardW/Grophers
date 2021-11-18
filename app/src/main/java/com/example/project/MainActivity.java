package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Writing to a realtime database
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Integer i = 1;
        ref.child("test").setValue(i);*/

        // Reading from a realtime database using a persistent listener
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students");
        ValueEventListener listener = new ValueEventListener() {
            @Overridepublic void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    Student student = child.getValue(Student.class);
                    Log.i("demo", student.toString());
                }
            }

            @Overridepublic void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);*/

        // Reading once from a realtime database
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students");
        ref.child("s1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Overridepublic void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                } else {
                    Log.i("demo", task.getResult().getValue().toString());
                }
            }
        });*/

    }
}