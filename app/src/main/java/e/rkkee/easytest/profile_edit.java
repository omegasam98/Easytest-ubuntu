package e.rkkee.easytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static e.rkkee.easytest.Login.mMyAppsBundlecon;

public class profile_edit extends AppCompatActivity implements View.OnClickListener {


    private EditText pname;
    private TextView pemail;
    private EditText pcontact;

    private Button save;
    private Button cancel;

    private String pnam;
    private String pmail;
    private String pcon;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        pname = (EditText) findViewById(R.id.editTextpname);
        pemail = (TextView) findViewById(R.id.editTextpemail);
        pcontact = (EditText) findViewById(R.id.editTextpcontact);

        save = (Button) findViewById(R.id.savebtn);
        cancel = (Button) findViewById(R.id.cnclbtn);

        pnam = mMyAppsBundlecon.getString("dame");
        pmail = mMyAppsBundlecon.getString("mail");
        pcon = mMyAppsBundlecon.getString("mon");

        pname.setText(pnam);
        pemail.setText(pmail);
        pcontact.setText(pcon);

        bundle.putString("nam",pnam);
        bundle.putString("con",pcon);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick (View view) {

        if(view == save) {
            modify_dat(pnam,pmail,pcon);
            finish();
            startActivity(new Intent(profile_edit.this,Login.class));
        }
        else
            if(view == cancel){
                finish();
                startActivity(new Intent(profile_edit.this, Login.class));
            }
    }

    private void modify_dat(String pnam, String pmail, String pcon) {
        DocumentReference washingtonRef = db.collection("users").document(pmail);


        pnam = pname.getText().toString();
        pcon = pcontact.getText().toString();

        washingtonRef
                .update("name",pnam,
                        "contact",pcon)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Data Update Successful", Toast.LENGTH_SHORT).show();
                        getData(pmail);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Data Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getData(String em){
        db.collection("users")
                .whereEqualTo("email",em)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc: task.getResult()){
                            pcon = doc.getString("name");
                            pnam = doc.getString("contact");

                            pname.setText(pnam);
                            pcontact.setText(pcon);
                        }
                    }
                });
    }
}
