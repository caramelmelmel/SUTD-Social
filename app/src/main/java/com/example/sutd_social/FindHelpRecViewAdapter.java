package com.example.sutd_social;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutd_social.firebase.Social;

import java.util.ArrayList;

public class FindHelpRecViewAdapter extends RecyclerView.Adapter<FindHelpRecViewAdapter.ViewHolder> {

    private final Context context;  // context have to be created in order for items to reference it
    private ArrayList<Find_Help> find_help_posts = new ArrayList<>(); // create new ArrayList to fit into Recycler View

    public FindHelpRecViewAdapter(Context context) {  // create constructor for Adapter Class
        this.context = context;   // constructor for context
    }

    @NonNull
    @Override
    // ViewGroup is parent of all layout files and is used to group different views inside it
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // this generates the view holder
        View view = LayoutInflater.from(parent.getContext()) //this inflates the view and requires context from ViewGroup parent
                .inflate(R.layout.find_help_posts, parent, false);  // in inflate need 3 arguements, 1st is address of layout of every item in recyclerView, 2nd is ViewGroup (Where you want to attach View object, if Main Activity layout is Relative, parent is also relative), 3rd is a boolean

        ViewHolder holder = new ViewHolder(view);
        return holder;                                                                                                                               // boolean is false when parent is known and true when parent is changed to null which shows that you do not know where the parent is located at
    }

    @Override   // on Click Listener is set here   , final added as position used in inner class
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {   //position is position of item inside recyclerView
        //holder.txtName.setText(contacts.get(position).getName());
//        holder.parent.setOnClickListener(new View.OnClickListener() {   // can just set onClick for whole parent Layout rather than individual widgets
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(context, contacts.get(position).getName() + " Selected",Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.StudentName.setText(find_help_posts.get(position).getName());
        holder.StudentPillar.setText(find_help_posts.get(position).getPillar());
//        if(!find_help_posts.get(position).getConfidence_lvl().toString().isEmpty()){
//            holder.MatchRate.setText(find_help_posts.get(position).getConfidence_lvl().toString());
//        }
        //holder.MatchRate.setText(find_help_posts.get(position).getConfidence_lvl().toString());
        holder.Skills.setText(find_help_posts.get(position).getSkills());
        Log.d("LoginActivity", "onBindViewHolder: " + find_help_posts.get(position).getConfidence_lvl());
        holder.MatchRate.setText(find_help_posts.get(position).getConfidence_lvl() + " %");
        Social.displayImage((Activity) context, find_help_posts.get(position).getProfilePicture(), holder.ProfilePicture);

    }

    @Override
    public int getItemCount() {
        return find_help_posts.size();
    } // returns count of data, number of items inside adapter

    public void setPosts(ArrayList<Find_Help> posts) {  // create setter for ArrayList as it is private and this can be used to allow access when in Main
        this.find_help_posts = posts;
        notifyDataSetChanged();  // This is to notify Recycler View to refresh when ArrayList is changed from the internet
    }

    public class ViewHolder extends RecyclerView.ViewHolder {   // inner view holder class will hold view for every item in recycler view

        private final CardView cardView;  // initiallize all widgets that were inside the new layout file
        private final TextView StudentName;
        private final TextView StudentPillar;
        private final TextView MatchRate;
        private final TextView Skills;
        private final ImageView ProfilePicture;

        //private RelativeLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);  // Have to initialize findViewByID through the view object in this class
            ProfilePicture = itemView.findViewById(R.id.cardProfilePicture);
            StudentName = itemView.findViewById(R.id.cardStudentName);
            StudentPillar = itemView.findViewById(R.id.cardStudentPillar);
            MatchRate = itemView.findViewById(R.id.cardMatchRate);
            Skills = itemView.findViewById(R.id.cardTagList);
            //parent = itemView.findViewById(R.id.parent);  // Have to instantiate to access from OnBindViewHolder Method
        }
    }
}
