package pl.pwr.s249319.babble.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import pl.pwr.s249319.babble.Prototypes.User;
import pl.pwr.s249319.babble.R;
import pl.pwr.s249319.babble.SendMessageActivity;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHold>{

    private Context context;
    private List<User> usersList;

    public UserAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_view, parent, false);
        return new UserAdapter.ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {

        User user = usersList.get(position);
        holder.username.setText(user.getUsername());

        if(user.getImageURL().equals("default")){
            holder.imageView.setImageResource(R.drawable.logo);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(context, SendMessageActivity.class);
                i.putExtra("userid", user.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView imageView;

        public ViewHold(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
