package e.rkkee.easytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

public class ban extends AppCompatActivity {
    ListView search_bank;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban);
        search_bank = (ListView) findViewById(R.id.search_place);

        ArrayList<String> arrayPlace = new ArrayList<>();
        arrayPlace.addAll(Arrays.asList(getResources().getStringArray(R.array.my_place)));

        adapter = new ArrayAdapter<String>(
                ban.this,
                android.R.layout.simple_list_item_1,
                arrayPlace
        );
        search_bank.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menuban, menu);
        MenuItem item = menu.findItem(R.id.search_bank);
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
        return super.onCreateOptionsMenu(menu);


    }
}
