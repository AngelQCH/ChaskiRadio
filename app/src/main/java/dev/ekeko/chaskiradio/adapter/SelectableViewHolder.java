package dev.ekeko.chaskiradio.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import dev.ekeko.chaskiradio.R;
import dev.ekeko.chaskiradio.model.Nominee;

/**
 * Creado por Ángel Quino Chipana  en 24/noviembre/2018
 * dangerouslapaz@gmail.com
 * +591 78812425 - +591 68092193
 * La Paz, Bolivia
 */
public class SelectableViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    Guideline guideline;
    AppCompatImageView photo;
    AppCompatTextView name,percentVotes;
    Nominee mItem;
    LottieAnimationView animation;
    View  percentBar,percentBackground;
    OnItemSelectedListener itemSelectedListener;
    Context context;

    public SelectableViewHolder(View view, OnItemSelectedListener listener) {
        super(view);
        itemSelectedListener = listener;
        guideline=view.findViewById(R.id.guideline);
        animation=view.findViewById(R.id.animation);
        name= view.findViewById(R.id.name);
        photo= view.findViewById(R.id.photo);
        percentVotes=view.findViewById(R.id.percentVotes);
        percentBar=view.findViewById(R.id.percentBar);
        percentBackground= view.findViewById(R.id.percentBackground);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                itemSelectedListener.onItemSelected(mItem);
            }
        });
    }

    public void setChecked(boolean value) {
        /*if (value) {
            textView.setBackgroundColor(Color.LTGRAY);
        } else {
            textView.setBackground(null);
        }*/
        mItem.setSelected(value);
        if(mItem.isSelected()){
            //custom_chip.setBackground(context.getResources().getDrawable(R.drawable.shape_chip_check));
            animation.setVisibility(View.VISIBLE);
            animation.playAnimation();
        }else{
            //custom_chip.setBackground(context.getResources().getDrawable(R.drawable.shape_chip));
            animation.setVisibility(View.GONE);
            if(animation.isAnimating()){
                animation.cancelAnimation();
                animation.clearAnimation();
            }
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Nominee item);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}