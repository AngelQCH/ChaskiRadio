package dev.ekeko.chaskiradio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import dev.ekeko.chaskiradio.R;
import dev.ekeko.chaskiradio.model.Nominee;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Creado por Ángel Quino Chipana  en 29/agosto/2019
 * dangerouslapaz@gmail.com
 * +591 78812425 - +591 68092193
 * La Paz, Bolivia
 */
public class SelectableAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private final List<Nominee> mValues;
    private boolean isMultiSelectionEnabled = false;
    SelectableViewHolder.OnItemSelectedListener listener;
    Context context;
    String itemSelec="";

    public SelectableAdapter(SelectableViewHolder.OnItemSelectedListener listener,
                             List<Nominee> items, boolean isMultiSelectionEnabled,Context context) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;
        this.context =context;
        mValues = new ArrayList<>();
        for (Nominee item : items) {
            mValues.add(item);
        }
    }

    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poll_item, parent, false);

        return new SelectableViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        holder.setContext(context);
        Nominee selectableItem = mValues.get(position);
        float percent=(selectableItem.getVotes()*1.0f)/100;
        String name = selectableItem.getName();
        Glide.with(context)
                .load(selectableItem.getPhoto())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_round_photo_24px))
                .transition(withCrossFade())
                .into(holder.photo);
        holder.guideline.setGuidelinePercent(percent);
        holder.name.setText(name);
        holder.percentVotes.setText(selectableItem.getVotes()+"%");
        holder.mItem = selectableItem;
        //holder.setChecked(holder.mItem.isSelected());

        if(selectableItem.isSelected()){
            itemSelec=selectableItem.getName();

            //holder.custom_chip.setBackground(context.getResources().getDrawable(R.drawable.shape_chip_check));
            holder.animation.setVisibility(View.VISIBLE);
            holder.animation.playAnimation();

        }else{
            //holder.custom_chip.setBackground(context.getResources().getDrawable(R.drawable.shape_chip));
            holder.animation.setVisibility(View.GONE);
            if(holder.animation.isAnimating()){
                holder.animation.cancelAnimation();
                holder.animation.clearAnimation();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<Nominee> getSelectedItems() {

        List<Nominee> selectedItems = new ArrayList<>();
        for (Nominee item : mValues) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    public String getOpciones() {

        String opciones="";
        int c=0;
        for (Nominee item : mValues) {
            if (item.isSelected()) {
                if(c==0)
                    opciones = item.getName();
                else
                    opciones=opciones+", "+item.getName();
                c++;
            }
        }
        return opciones;
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return SelectableViewHolder.MULTI_SELECTION;
        }
        else{
            return SelectableViewHolder.SINGLE_SELECTION;
        }
    }
    public String getItemSelec(){
        return itemSelec;
    }
    public String getItemsSelec(){
        String with="";
        for(Nominee s:mValues){
            if(s.isSelected())
                with=with+" "+s.getName();
        }
        return with;
    }
    @Override
    public void onItemSelected(Nominee item) {
        if (!isMultiSelectionEnabled) {

            for (Nominee selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);
    }
    public ArrayList<Nominee> getSelection(){
        ArrayList<Nominee> sel=new ArrayList<>();
        for(Nominee s:mValues){
            if(s.isSelected())
                sel.add(s);
        }
        return sel;
    }
}