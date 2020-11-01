package dev.ekeko.chaskiradio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import dev.ekeko.chaskiradio.PodcastActivity;
import dev.ekeko.chaskiradio.R;
import dev.ekeko.chaskiradio.model.Preview;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder> {

    private ArrayList<Preview> previews;
    private int resource;
    private Context context;
    //MediaPlayer mediaPlayer = new MediaPlayer();
    public PreviewAdapter(ArrayList<Preview> previews, int resource, Context context) {
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
        if(preview.getPhoto()!=null&&!preview.getPhoto().equals("")){
            if(preview.getPhoto().length()>=2){
                Glide.with(context)
                        .load(preview.getPhoto())
                        .apply(RequestOptions.circleCropTransform())
                        .transition(withCrossFade())
                        .into(holder.photo);
            }else{
                switch (Integer.parseInt(preview.getPhoto())){
                    case 0:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.radio_art));
                        break;
                    case 1:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_metronome));
                        break;
                    case 2:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_microphone));
                        break;
                    case 3:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clave_sol));
                        break;
                    case 4:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_metronome));
                        break;
                    case 5:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_microphone));
                        break;
                    default:
                        holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clave_sol));
                        break;
                }
            }
        }else{
            //Mostrar algo por defecto, por ejemplo una ilustración de radio o de disco o de problemas técnicos
        }
        holder.name.setText(preview.getName());
        switch (preview.getType()){
            case 0:
                holder.type.setText("PROGRAMADO");
                break;
            case 1:
                holder.type.setText("ESPECIAL");
                break;
            default:
                holder.type.setText("PROGRAMADO");
                break;
        }
        holder.info.setText(preview.getDate()+", "+preview.getCounter()+" min");
    }

    @Override
    public int getItemCount() {
        return previews.size();
    }

    public class PreviewViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView photo;
        private AppCompatTextView name,type,info;

        public PreviewViewHolder(View itemView) {
            super(itemView);
            photo= itemView.findViewById(R.id.photo);
            name= itemView.findViewById(R.id.name);
            type= itemView.findViewById(R.id.type);
            info= itemView.findViewById(R.id.info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Preview previewSel = previews.get(getAdapterPosition());
                    Intent intent = new Intent(context, PodcastActivity.class);
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
