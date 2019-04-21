package e.rkkee.easytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class register extends AppCompatActivity implements View.OnClickListener {

    private Button mbuttonRegister;
    private EditText meditTextFname;
    private EditText meditTextLname;
    private EditText meditTextContact;
    private EditText meditTextEmail;
    private EditText meditTextPassword;
    private EditText meditTextCPassword;
    private TextView mtextViewSignin;

    private FirebaseAuth mAuth;


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();



        //initializing views
        meditTextFname = findViewById(R.id.editTextFname);
        meditTextLname = findViewById(R.id.editTextLname);
        meditTextEmail = findViewById(R.id.editTextEmail);
        meditTextContact = findViewById(R.id.editTextContact);
        meditTextPassword = findViewById(R.id.editTextPassword);
        meditTextCPassword = findViewById(R.id.editTextCPassword);
        mtextViewSignin = findViewById(R.id.textViewSignin);

        mbuttonRegister = findViewById(R.id.buttonRegister);


             //if getCurrentUser does not returns null
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            startActivity(new Intent(register.this, Login.class));
        }


        //attaching listener to button
        mbuttonRegister.setOnClickListener(this);
        mtextViewSignin.setOnClickListener(this);
    }

    private void registerUser() {



        //getting email and password from edit texts;
        final String email = meditTextEmail.getText().toString().trim();
        final String password = meditTextPassword.getText().toString().trim();
        final String cpassword = meditTextCPassword.getText().toString().trim();
        final String fname = meditTextFname.getText().toString().trim();
        final String lname = meditTextLname.getText().toString().trim();
        final String contact = meditTextContact.getText().toString();
        final String name = fname + " " +lname;

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(cpassword) || password.length()<6) {
            Toast.makeText(getApplicationContext(), "Please enter valid password", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.contentEquals(cpassword)) {
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }
        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user1 = new User(name,email,contact);
                            writeNewUser(user1);
                            Toast.makeText(register.this,"Registration Complete",Toast.LENGTH_LONG).show();

                        }
                        else{
                            if(mAuth.getCurrentUser() != null){
                                //that means user is already logged in
                                //so close this activity
                                Toast.makeText(register.this, "User Exists", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register.this, MainActivity.class));
                            }
                            else
                            Toast.makeText(register.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public void onClick (View view){

        if (view == mbuttonRegister) {
             registerUser();
        }

        else if(view == mtextViewSignin){
            startActivity(new Intent(register.this, Login.class));
        }


    }


    private void writeNewUser(User user1) {


        Map<String, Object> user = new HashMap<>();
        user.put("name", user1.name);
        user.put("email", user1.email);
        user.put("contact", user1.contact);

// Add a new document with a generated ID
        db.collection("users").document(user1.email).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(register.this, "adding document", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(register.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, "Error adding document", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });


    }



}




