package tr.yildiz.edu.sermedkerim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityListViewHolder>{

    ArrayList<Etkinlik> etkinlikListesi;
    Context context;

    public ActivityListAdapter(ArrayList<Etkinlik> etkinlikListesi, Context context) {
        this.etkinlikListesi = etkinlikListesi;
        this.context = context;
    }

    public static class ActivityListViewHolder extends RecyclerView.ViewHolder{

        TextView name,date,location;
        ImageButton imageButton;

        public ActivityListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewRecyclerEtkinlikIsmi);
            date = itemView.findViewById(R.id.textViewRecylerActivityDate);
            location = itemView.findViewById(R.id.textViewRecyclerActivityLocation);
            imageButton = itemView.findViewById(R.id.imageButtonUpdateActivityDetails);
        }
    }

    @NonNull
    @Override
    public ActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_activityinfo,parent,false);
        ActivityListAdapter.ActivityListViewHolder holder = new ActivityListAdapter.ActivityListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListViewHolder holder, int position) {
        holder.name.setText(etkinlikListesi.get(position).getName());
        holder.date.setText(etkinlikListesi.get(position).getDate());
        holder.location.setText(etkinlikListesi.get(position).getLocation());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ActivityDetailsUpdate.class);
                intent.putExtra("updateposition",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return etkinlikListesi.size();
    }
}
