package team.project.owlize;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManualAdapter extends RecyclerView.Adapter<ManualAdapter.ManualViewHolder> {

    private ArrayList<String> courses;
    String courseName;
TextView textView;

    public ManualAdapter(ArrayList<String> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ManualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_group, parent, false);
        return new ManualViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManualViewHolder holder, int position) {
        courseName = courses.get(position);
        holder.courseName.setText(courseName);
    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ManualViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        RecyclerView manualRecycler;

        public ManualViewHolder(@NonNull TextView itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.tv_name);
            manualRecycler = itemView.findViewById(R.id.rv_member);
        }
    }
}
