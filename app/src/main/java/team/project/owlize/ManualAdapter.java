package team.project.owlize;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManualAdapter extends RecyclerView.Adapter<ManualAdapter.ManualViewHolder> {
    private Activity activity;
    ArrayList<String> assignmentListGroup;
    ArrayList<String> courseListGroup;
    String assignments;


    public ManualAdapter(Activity activity, ArrayList<String> assignmentListGroup, ArrayList<String> courseListGroup) {
        this.activity = activity;
        this.assignmentListGroup = assignmentListGroup;
        this.courseListGroup = courseListGroup;
    }

    @NonNull
    @Override
    public ManualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_group, parent, false);
        return new ManualAdapter.ManualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManualViewHolder holder, int position) {
        holder.tvName.setText(assignmentListGroup.get(position));
        ArrayList<String> arrayListMember = new ArrayList<>();
//SOME CONTENT HERE!!!!!!!!!!!!!!!!!!!!!
        MemberAdp adapterMember = new MemberAdp(arrayListMember);

        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity);

        holder.rvMember.setLayoutManager(layoutManagerMember);

        holder.rvMember.setAdapter(adapterMember);
    }


    @Override
    public int getItemCount() {
        return assignmentListGroup.size();
    }

    public class ManualViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        RecyclerView rvMember;

        public ManualViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            rvMember = itemView.findViewById(R.id.rv_member);
        }
    }
}
