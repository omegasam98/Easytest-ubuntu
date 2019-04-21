package e.rkkee.easytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    ListView search_place;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        search_place = (ListView) findViewById(R.id.search_place);

        ArrayList<String> arrayPlace = new ArrayList<>();
        arrayPlace.addAll(Arrays.asList(getResources().getStringArray(R.array.my_place)));

        adapter = new ArrayAdapter<String>(
                Main2Activity.this,
                android.R.layout.simple_list_item_1,
                arrayPlace
        );
        search_place.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_place);
        String c = item.toString();
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }


        });

        search_place.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String selection = (String) arg0.getItemAtPosition(arg2);

                if(selection.equalsIgnoreCase("abbb mall"))
                {
                    Intent I = new Intent(Main2Activity.this,ban.class);
                }

            }
        });

        return super.onCreateOptionsMenu(menu);



    }
}
