package team.project.owlize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Member;
import java.util.ArrayList;

/**
 * member adapter for helping build the Canvas API
 */
public class MemberAdp extends RecyclerView.Adapter<MemberAdp.ViewHolder> {

    ArrayList<String> arrayListMember;
    ArrayList<String> arrayListDate;

    /**
     * constructor
     */
    public MemberAdp(ArrayList<String> arrayListMember, ArrayList<String> arrayListDate) {

        this.arrayListMember = arrayListMember;
        this.arrayListDate = arrayListDate;
    }

    /**
     * gets the view and shows the content within it
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_member,parent,false);

        return new MemberAdp.ViewHolder(view);
    }

    /**
     * binds the data to the view
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(arrayListMember.get(position));
        holder.tvDue.setText(arrayListDate.get(position));

    }

    /**
     * gets the list length of the array
     */
    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    /**
     * creates the view for the list items
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDue;

        /**
         * gets the data for the view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDue = itemView.findViewById(R.id.tv_DueDate);

        }
    }
}
