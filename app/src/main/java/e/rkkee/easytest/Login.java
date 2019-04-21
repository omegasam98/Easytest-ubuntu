package e.rkkee.easytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity implements View.OnClickListener {



    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button tbuttonlogin;
    private TextView tsignup;
    private TextView forgotp;

    public String xemail, xname, xcon;

    User user = new User();


    //Intent intent = new Intent(Login.this, MainActivity.class);


    public static Bundle mMyAppsBundlecon = new Bundle();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        tbuttonlogin = findViewById(R.id.buttonlogin);
        tsignup = findViewById(R.id.signup);

        Login.this.getIntent().putExtras(mMyAppsBundlecon);

        forgotp = findViewById(R.id.forgotp);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        FirebaseUser userx = FirebaseAuth.getInstance().getCurrentUser();


        //if getCurrentUser does not returns null
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            retnam();
            startActivity(new Intent(Login.this, MainActivity.class));
        }

        if (userx != null) {
            xemail = userx.getEmail();

            Toast.makeText(Login.this, xemail, Toast.LENGTH_SHORT).show();

            mMyAppsBundlecon.putString("mail",xemail);

            // Check if user's email is verified
            boolean emailVerified = userx.isEmailVerified();

        }

        retnam();
        //mMyAppsBundlecon.putString("name",xname);
        //mMyAppsBundlecon.putString("con",xcon);


        tbuttonlogin.setOnClickListener(this);
        tsignup.setOnClickListener(this);
        forgotp.setOnClickListener(this);
    }

    public void retnam()
    {

        db.collection("users")
                .whereEqualTo("email",xemail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc: task.getResult()){
                            user.setName(doc.getString("name"));
                            user.setEmail(doc.getString("email"));
                            user.setContact(doc.getString("contact"));
                            xname = user.getName();
                            xcon = user.getContact();
                            mMyAppsBundlecon.putString("dame",xname);
                            mMyAppsBundlecon.putString("mon",xcon);
                            Toast.makeText(Login.this, xname, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void beginLogin(){

        String temail = email.getText().toString().trim();
        String tpassword = password.getText().toString().trim();



        if (TextUtils.isEmpty(temail)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(tpassword)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();
            Login.this.finish();
            //and open profile activity
            startActivity(new Intent(Login.this, MainActivity.class));
        }


        mAuth.signInWithEmailAndPassword(temail, tpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Login.this.finish();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }




    @Override
    public void onClick (View view) {

        if (view == tbuttonlogin) {
            beginLogin();
        }

        else if(view == tsignup){
            startActivity(new Intent(Login.this, register.class));
        }

        else if (view == forgotp){
            startActivity(new Intent(Login.this,Forgotpass.class));
        }
    }


}
