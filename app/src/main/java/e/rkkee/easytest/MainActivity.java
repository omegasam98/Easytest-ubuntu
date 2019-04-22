package e.rkkee.easytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executors;

import static e.rkkee.easytest.Login.mMyAppsBundlecon;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String ema;
    private String nam;
    private String con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ema = mMyAppsBundlecon.getString("mail");
        nam = mMyAppsBundlecon.getString("dame");
        con = mMyAppsBundlecon.getString("mon");
        getData(ema);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView textViewName = (TextView) header.findViewById(R.id.textViewName);
        textViewName.setText(nam);
        Toast.makeText(MainActivity.this, nam, Toast.LENGTH_SHORT).show();
        TextView textViewEmail = (TextView) header.findViewById(R.id.textViewEmail);
        textViewEmail.setText(ema);
    }


    private void getData(String em){
        db.collection("users")
                .whereEqualTo("email",em)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc: task.getResult()){
                            nam = doc.getString("name");
                            con = doc.getString("contact");
                        }
                    }
                });
    }


    @Override
    public void onBackPressed()
    {
        startActivityForResult(new Intent(MainActivity.this, MainActivity.class),1);

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Executors.newSingleThreadExecutor().execute(() -> {
                Intent intent1 = new Intent(getApplicationContext(), profile_edit.class);
                startActivity(intent1);
            });

            // Handle the camera action|
        } else if (id == R.id.nav_trans) {


        } else if (id == R.id.nav_paymeth) {

        } else if (id == R.id.nav_car) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.signout){

            mAuth.getInstance()
                    .signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
