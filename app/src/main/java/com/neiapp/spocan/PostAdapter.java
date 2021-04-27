package com.neiapp.spocan;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter{

}
/*
public class PostAdapter  extends  RecyclerView.Adapter<PostAdapter.Viewholder>{

    private Context mContext;
    private List<Post> mPosts;
    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts){
        this.mContext = mContext;
        this.mPosts = mPosts;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Post post = mPosts.get(position);
        Picasso.get().load(post.getImageurl()).into(holder.post_image);
        holder.description.setText(post.getDescription());

        FirebaseDatabase.getInstance().getReference().child("Users").child(post.getPublisher()).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                User user = dataSnapshot.getValue(User.class);
                Picasso.get().load(user.getImageurl()).into(holder.imageProfile)
                        holder.username.setText(user.getUsername());
            }

            @Overridepublic void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public ImageView imageProfile;
        public ImageView post_image;
        public ImageView like;

        public TextView username;
        public TextView nro_likes;
        public TextView description;

        public Viewholder(@NonNull View itemView){
            super(itemView);

            imageProfile = itemView.findViewById(R.id.imageView);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);

            username = itemView.findViewById(R.id.username);
            nro_likes = itemView.findViewById(R.id.likes_number);
            description = itemView.findViewById(R.id.description);
        }
    }

}*/