package team.project.owlize;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private Activity activity;
    ArrayList<String> arrayListGroup;
    ArrayList<String> courseListGroup;
    String assignments;

    CustomAdapter(Activity activity, ArrayList<String> arrayListGroup, ArrayList<String> courseListGroup) {
        this.activity = activity;
        this.arrayListGroup = arrayListGroup;
        this.courseListGroup = courseListGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_group, parent, false);

        return new CustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(arrayListGroup.get(position));
        ArrayList<String> arrayListMember = new ArrayList<>();
        ArrayList<String> arrayListDate = new ArrayList<>();

        String url = "https://byui.instructure.com:443/api/v1/courses/"+courseListGroup.get(position)+"/assignments?bucket=future&order_by=due_at";
        assignments = getJSON(url);
        Log.d("test", assignments);
        Gson gson = new Gson();
        Type type = new TypeToken<List<AssignmentModel>>(){}.getType();
        List<AssignmentModel> assignmentList = gson.fromJson(assignments, type);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Format f = new SimpleDateFormat("MM/dd/yy");

        for (AssignmentModel assignment : assignmentList) {

            arrayListMember.add(assignment.name);

//            arrayListDate.add(assignment.due_at);

            try {
                if (assignment.due_at != null) {
                    Date newDate = sdf.parse(assignment.due_at.substring(0,10));
                    String test = f.format(newDate);
                    arrayListDate.add(test);
                } else {
                    arrayListDate.add("No Due Date");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }




        }
        if (arrayListDate.isEmpty()) {
            arrayListDate.clear();
        }
        MemberAdp adapterMember = new MemberAdp(arrayListMember, arrayListDate);

        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity);

        holder.rvMember.setLayoutManager(layoutManagerMember);

        holder.rvMember.setAdapter(adapterMember);


    }

    public static String getJSON(String url) {
        HttpURLConnection con = null;

        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("Authorization","Bearer "+"10706~cymH1yYoCAH6CFifZpG54aJvgz1veW5Oxx4s5TXzf0L6pH4bygrIWXrOqHvkXaNu");

            con.setRequestProperty("Content-Type","application/json");
            con.setRequestMethod("GET");


            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDue;
        RecyclerView rvMember;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDue = itemView.findViewById(R.id.tv_DueDate);
            rvMember = itemView.findViewById(R.id.rv_member);
        }
    }


}
