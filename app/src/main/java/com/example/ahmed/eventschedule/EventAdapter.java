package com.example.ahmed.eventschedule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {
    private ArrayList<Event> events;
    private Context context;

    EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.eventName.setText(events.get(position).getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy h:mm a", Locale.ENGLISH);
        holder.eventDate.setText(dateFormat.format(events.get(position).getStartDate().getTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class)
                        .putExtra("position", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment.controlRealm.deleteEvent(events.get(holder.getAdapterPosition()).getId());
                events.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    Boolean isEmpty() {
        return events.isEmpty();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    void setFilter(ArrayList<Event> newEvents) {
        events = new ArrayList<>();
        events.addAll(newEvents);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView eventName, eventDate;
        ImageButton button;

        Holder(final View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
            button = (ImageButton) itemView.findViewById(R.id.event_delete);
        }
    }
}
