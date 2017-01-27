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
    private MainFragment mainFragment;

    public EventAdapter(Context context, MainFragment mainFragment) {
        this.context = context;
        this.mainFragment = mainFragment;
    }

    public void changeData(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Event event = events.get(position);

        holder.eventName.setText(event.getName());
        holder.icon.setColorFilter(Color.parseColor(event.getColor()));
        holder.iconText.setText(String.valueOf(event.getName().charAt(0)).toUpperCase());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy h:mm a", Locale.ENGLISH);
        holder.eventDate.setText(dateFormat.format(event.getStartDate().getTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class)
                        .putExtra("event_id", events.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment.controlRealm.deleteEvent(events.get(holder.getAdapterPosition()).getId());

                //delete from original ArrayList
                if (mainFragment.page == 0) {
                    mainFragment.upEvents.remove(holder.getAdapterPosition());
                    changeData(mainFragment.upEvents);
                } else {
                    mainFragment.doneEvents.remove(holder.getAdapterPosition());
                    changeData(mainFragment.doneEvents);
                }
                notifyItemRemoved(holder.getAdapterPosition());
                mainFragment.setEmptyListText();
            }
        });
    }

    public Boolean isEmpty() {
        return events.isEmpty();
    }

    public void removeAll() {
        if (mainFragment.page == 0)
            mainFragment.upEvents.clear();
        else
            mainFragment.doneEvents.clear();
        events.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setFilter(String newText, ArrayList<Event> list) {
        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event event : list) {
            String name = event.getName().toLowerCase();
            if (name.contains(newText))
                newEvents.add(event);
        }
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
