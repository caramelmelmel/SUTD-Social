package com.example.sutd_social;

import android.app.Activity;
import android.content.Context;
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

    public FindHelpRecViewAdapter(Context context) {
        this.context = context;   // constructor for context
    }

    @NonNull
    @Override
    // ViewGroup is parent of all layout files and is used to group different views inside it
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // this generates the view holder
        View view = LayoutInflater.from(parent.getContext()) //this inflates the view and requires context from ViewGroup parent
                .inflate(R.layout.find_help_posts, parent, false);  // in inflate need 3 arguments, 1st is address of layout of every item in recyclerView, 2nd is ViewGroup (Where you want to attach View object, if Main Activity layout is Relative, parent is also relative), 3rd is a boolean

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override   // on Click Listener is set here   , final added as position used in inner class
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {   //position is position of item inside recyclerView

        holder.StudentName.setText(find_help_posts.get(position).getName());
        holder.StudentPillar.setText(find_help_posts.get(position).getPillar());
        holder.Skills.setText(find_help_posts.get(position).getSkills());
        holder.MatchRate.setText(find_help_posts.get(position).getConfidence_lvl() + " %");
        Social.displayImage((Activity) context, find_help_posts.get(position).getProfilePicture(), holder.ProfilePicture);

    }

    @Override
    public int getItemCount() {
        return find_help_posts.size();
    }

    public void setPosts(ArrayList<Find_Help> posts) {
        this.find_help_posts = posts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final TextView StudentName;
        private final TextView StudentPillar;
        private final TextView MatchRate;
        private final TextView Skills;
        private final ImageView ProfilePicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            ProfilePicture = itemView.findViewById(R.id.cardProfilePicture);
            StudentName = itemView.findViewById(R.id.cardStudentName);
            StudentPillar = itemView.findViewById(R.id.cardStudentPillar);
            MatchRate = itemView.findViewById(R.id.cardMatchRate);
            Skills = itemView.findViewById(R.id.cardTagList);

        }
    }
}
