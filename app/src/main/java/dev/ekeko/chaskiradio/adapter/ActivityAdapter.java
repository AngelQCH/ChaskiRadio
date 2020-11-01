package dev.ekeko.chaskiradio.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import dev.ekeko.chaskiradio.PodcastActivity;
import dev.ekeko.chaskiradio.PollActivity;
import dev.ekeko.chaskiradio.R;
import dev.ekeko.chaskiradio.model.Preview;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.PreviewViewHolder> {

    private ArrayList<Preview> previews;
    private int resource;
    private Context context;
    //MediaPlayer mediaPlayer = new MediaPlayer();
    public ActivityAdapter(ArrayList<Preview> previews, int resource, Context context) {
        this.previews = previews;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new PreviewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PreviewViewHolder holder, final int pos) {
        Preview preview = previews.get(pos);
        //holder.cardView.setCardBackgroundColor(Color.parseColor(preview.getColor()));
        if(preview.getUrlStreaming()!=null&&!preview.getUrlStreaming().equals(""))
            holder.cardView.setCardBackgroundColor(Color.parseColor(preview.getUrlStreaming()));
        holder.name.setText(preview.getName());
        switch (preview.getType()){
            case 0:
                holder.type.setText("VOTACIÓN");
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ballot));
                holder.counter.setText(preview.getCounter()+" votos");
                break;
            case 1:
                holder.type.setText("FORM");
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_promotion));
                holder.counter.setText(preview.getCounter()+" llenados");
                break;
            default:
                holder.type.setText("SORTEO");
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tickets));
                holder.counter.setText(preview.getCounter()+" participantes");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return previews.size();
    }

    public class PreviewViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private AppCompatImageView image;
        private AppCompatTextView name,type,counter;

        public PreviewViewHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardActivity);
            image= itemView.findViewById(R.id.image);
            name= itemView.findViewById(R.id.name);
            type= itemView.findViewById(R.id.type);
            counter= itemView.findViewById(R.id.counter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Preview previewSel = previews.get(getAdapterPosition());
                    Intent intent = new Intent(context, PollActivity.class);
                    intent.putExtra("previewSel", previewSel);
                    context.startActivity(intent);
                }
            });
        }
    }
    public void setFilter(ArrayList<Preview> previews){
        this.previews =new ArrayList<>();
        this.previews.addAll(previews);
        notifyDataSetChanged();
    }

}
