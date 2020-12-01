package com.example.sutd_social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutd_social.firebase.BulletinBoard;

import java.util.ArrayList;

public class BulletinBoardPostRecViewAdapter extends RecyclerView.Adapter<BulletinBoardPostRecViewAdapter.ViewHolder>{ // this means that datatype of Adapter is the viewHolder Class

    private ArrayList<BulletinBoardPost> posts = new ArrayList<>(); // create new ArrayList to fit into Recycler View

    private final Context context;  // context have to be created in order for items to reference it
    private final int activity_code_inner = 2;

    public BulletinBoardPostRecViewAdapter(Context context) {  // create constructor for Adapter Class
        this.context = context;   // constructor for context
    }

    @NonNull
    @Override                // ViewGroup is parent of all layout files and is used to group different views inside it
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // this generates the view holder
        View view = LayoutInflater.from(parent.getContext()) //this inflates the view and requires context from ViewGroup parent
                .inflate(R.layout.bulletin_board_post, parent, false);  // in inflate need 3 arguements, 1st is address of layout of every item in recyclerView, 2nd is ViewGroup (Where you want to attach View object, if Main Activity layout is Relative, parent is also relative), 3rd is a boolean

        ViewHolder holder = new ViewHolder(view);
        return holder;                                                                                                                               // boolean is false when parent is known and true when parent is changed to null which shows that you do not know where the parent is located at
    }

    @Override   // on Click Listener is set here   , final added as position used in inner class
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {   //position is position of item inside recyclerView
        //holder.txtName.setText(contacts.get(position).getName());
//        holder.BulletinBoardParent.setOnClickListener(new View.OnClickListener() {   // can just set onClick for whole parent Layout rather than individual widgets
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(context, contacts.get(position).getName() + " Selected",Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.postTitle.setText(posts.get(position).getPostTitle());
        holder.postDescription.setText(posts.get(position).getPostDescription());
        //adding image
        BulletinBoard.displayImage((Activity) context, posts.get(position).getBulletin_url(),holder.cardImage);

        holder.BulletinBoardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity origin = (Activity) context;

                origin.startActivityForResult(new Intent(context,Bulletin_inner_post_popup.class),activity_code_inner);

            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    } // returns count of data, number of items inside adapter

    public void setPosts(ArrayList<BulletinBoardPost> posts) {  // create setter for ArrayList as it is private and this can be used to allow access when in Main
        this.posts = posts;
        notifyDataSetChanged();  // This is to notify Recycler View to refresh when ArrayList is changed from the internet
    }

    public class ViewHolder extends RecyclerView.ViewHolder{   // inner view holder class will hold view for every item in recycler view

        private final TextView postTitle;
        private final TextView postDescription;  // initiallize all widgets that were inside the new layout file
        private final CardView BulletinBoardParent;
        private ImageView cardImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //card_title, card_content
            postTitle = itemView.findViewById(R.id.card_title);  // Have to initialize findViewByID through the view object in this class
            postDescription = itemView.findViewById(R.id.card_content);
            BulletinBoardParent = itemView.findViewById(R.id.bulletin_board_post_layout);  // Have to instantiate to access from OnBindViewHolder Method
            cardImage = itemView.findViewById(R.id.card_image);
        }
    }
}
