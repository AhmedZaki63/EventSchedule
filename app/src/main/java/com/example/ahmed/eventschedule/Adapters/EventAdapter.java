package com.example.ahmed.eventschedule.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.eventschedule.DetailsActivity;
import com.example.ahmed.eventschedule.Event;
import com.example.ahmed.eventschedule.MainFragment;
import com.example.ahmed.eventschedule.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {
    private ArrayList<Event> events;
    private Context context;

    public EventAdapter(Context context, ArrayList<Event> events) {
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
        Event event = events.get(position);
        holder.eventName.setText(event.getName());
        //String[] arrayOfStrings = context.getResources().getStringArray(R.array.colors);
        //String randomColor = arrayOfStrings[new Random().nextInt(arrayOfStrings.length)];
        holder.icon.setColorFilter(Color.parseColor(event.getColor()));
        holder.iconText.setText(String.valueOf(event.getName().charAt(0)).toUpperCase());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy h:mm a", Locale.ENGLISH);
        holder.eventDate.setText(dateFormat.format(event.getStartDate().getTime()));

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

    public Boolean isEmpty() {
        return events.isEmpty();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setFilter(ArrayList<Event> newEvents) {
        events = new ArrayList<>();
        events.addAll(newEvents);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView eventName, eventDate, iconText;
        ImageButton button;
        ImageView icon;

        Holder(final View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
            button = (ImageButton) itemView.findViewById(R.id.event_delete);
            icon = (ImageView) itemView.findViewById(R.id.event_icon);
            iconText = (TextView) itemView.findViewById(R.id.event_icon_text);
        }
    }
}
