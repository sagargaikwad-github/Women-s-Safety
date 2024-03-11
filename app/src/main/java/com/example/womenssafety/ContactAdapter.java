package com.example.womenssafety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.holder> {
    ArrayList<PhoneModel> arrayList = new ArrayList<>();
    Context context;
    deleteContact deleteContact;

    public ContactAdapter(ArrayList<PhoneModel> arrayList, Context context, com.example.womenssafety.deleteContact deleteContact) {
        this.arrayList = arrayList;
        this.context = context;
        this.deleteContact = deleteContact;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_rv_item, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.phone_display_tv.setText(arrayList.get(position).getPhone());
        holder.name_display_tv.setText(arrayList.get(position).getName());


        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContact.deleteContact(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class holder extends RecyclerView.ViewHolder {
        TextView phone_display_tv, name_display_tv;
        ImageView deleteContact;

        public holder(@NonNull View itemView) {
            super(itemView);

            name_display_tv = itemView.findViewById(R.id.name_display_tv);
            phone_display_tv = itemView.findViewById(R.id.phone_display_tv);
            deleteContact = itemView.findViewById(R.id.deleteContact);
        }
    }
}
