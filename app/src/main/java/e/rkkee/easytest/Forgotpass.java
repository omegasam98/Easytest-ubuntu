package e.rkkee.easytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpass extends AppCompatActivity implements View.OnClickListener {

    private EditText email1;

    private Button forgotpass;

    private TextView returnsign;

    private FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);


        mAuth1 = FirebaseAuth.getInstance();

        email1 = findViewById(R.id.email1);

        forgotpass = findViewById(R.id.forogtpass);

        returnsign = findViewById(R.id.returnsign);

        forgotpass.setOnClickListener(this);
        returnsign.setOnClickListener(this);

    }

    private void forgot(){

        final String email2 = email1.getText().toString().trim();
        if(email2.isEmpty())
        {
            Toast.makeText(Forgotpass.this,"Empty Fiels",Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Forgotpass.this,"Email has been sent",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Forgotpass.this,"Invalid email",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick (View view){
        if(view == forgotpass)
        {
            forgot();
        }

        else if(view == returnsign){
            startActivity(new Intent(Forgotpass.this,Login.class));
        }
    }
}
