package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CombineListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_list);

        recyclerView = findViewById(R.id.RecyclerViewCombines);
        recyclerView.setLayoutManager(new LinearLayoutManager(CombineListActivity.this));
        CombineListAdapter adapter = new CombineListAdapter(Clothes.combines,CombineListActivity.this);
        recyclerView.setAdapter(adapter);
    }
}