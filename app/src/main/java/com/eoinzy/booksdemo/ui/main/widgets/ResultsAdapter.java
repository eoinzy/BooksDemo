package com.eoinzy.booksdemo.ui.main.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eoinzy.booksdemo.R;
import com.eoinzy.booksdemo.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private final List<Item> items;

    public ResultsAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.book_title);
            description = itemView.findViewById(R.id.book_description);
            thumbnail = itemView.findViewById(R.id.book_image);
        }

        public void bind(Item item) {
            title.setText(item.getVolumeInfo().getTitle());
            if (item.getSearchInfo() != null && item.getSearchInfo().getTextSnippet() != null) {
                description.setText(item.getSearchInfo().getTextSnippet());
            }
            if (item.getVolumeInfo().getImageLinks() != null) {
                Picasso.get().load(item.getVolumeInfo().getImageLinks().getThumbnail().replace("http://", "https://")).into(thumbnail);
            }
        }
    }
}
