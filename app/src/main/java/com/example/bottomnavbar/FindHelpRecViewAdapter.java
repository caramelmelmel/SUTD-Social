package com.example.bottomnavbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindHelpRecViewAdapter extends RecyclerView.Adapter<FindHelpRecViewAdapter.ViewHolder> {

    private ArrayList<Find_Help> find_help_posts = new ArrayList<>(); // create new ArrayList to fit into Recycler View

    private Context context;  // context have to be created in order for items to reference it

    public FindHelpRecViewAdapter(Context context) {  // create constructor for Adapter Class
        this.context = context;   // constructor for context
    }

    @NonNull
    @Override                // ViewGroup is parent of all layout files and is used to group different views inside it
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

    }

    @Override
    public int getItemCount() {
        return find_help_posts.size();
    } // returns count of data, number of items inside adapter

    public void setPosts(ArrayList<Find_Help> posts) {  // create setter for ArrayList as it is private and this can be used to allow access when in Main
        this.find_help_posts = posts;
        notifyDataSetChanged();  // This is to notify Recycler View to refresh when ArrayList is changed from the internet
    }

    public class ViewHolder extends RecyclerView.ViewHolder{   // inner view holder class will hold view for every item in recycler view

        private TextView temp_txtview;  // initiallize all widgets that were inside the new layout file
        //private RelativeLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temp_txtview = itemView.findViewById(R.id.temp_textView);  // Have to initialize findViewByID through the view object in this class

            //parent = itemView.findViewById(R.id.parent);  // Have to instantiate to access from OnBindViewHolder Method
        }
    }
}
