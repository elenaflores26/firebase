package com.example.login_register_firebase;
import android.content.Context;
import android.icu.text.MessageFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<User> arrayList;
    OnItemClickListener onItemClickListener;
    public UserAdapter(Context context, ArrayList<User> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(MessageFormat.format("{0} {1}",arrayList.get(position).getFirstName(),arrayList.get(position).getLastName()));
        holder.phone.setText(arrayList.get(position).getPhone());
        holder.bio.setText(arrayList.get(position).getBio());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(arrayList.get(position)));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,bio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.list_item_name);
            phone = itemView.findViewById(R.id.list_item_phone);
            bio = itemView.findViewById(R.id.list_item_bio);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(User user);

    }
}
