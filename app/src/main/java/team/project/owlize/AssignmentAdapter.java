package team.project.owlize;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AssignmentAdapter extends BaseAdapter {

    private Context mContext;
    private List<Assignment> mTaskList;

    // Constructor


    public AssignmentAdapter(Context mContext, List<Assignment> mTaskList) {
        this.mContext = mContext;
        this.mTaskList = mTaskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.task_item, null);
        TextView taskName = (TextView)v.findViewById(R.id.task_desc);
        TextView taskDate = (TextView)v.findViewById(R.id.task_date);

        // Set text for TextView
        taskName.setText(mTaskList.get(position).getName());
        taskDate.setText(mTaskList.get(position).getDate());

        // Save task id to tag
        v.setTag(mTaskList.get(position).getId());
        return v;
    }
}
