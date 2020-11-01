package dev.ekeko.chaskiradio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import dev.ekeko.chaskiradio.model.Nominee;
import dev.ekeko.chaskiradio.adapter.SelectableAdapter;
import dev.ekeko.chaskiradio.adapter.SelectableViewHolder;

public class PollActivity extends AppCompatActivity {
    CoordinatorLayout coordinator_podcast;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ActionBar ab;
    AppBarLayout appBarLayout;
    AppCompatImageView headImage;
    AppCompatTextView headName;
    LinearLayoutManager mLManager;
    public static RecyclerView pollRecycler;
    public static SelectableAdapter colorsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        enlaceUI();
    }
    public void enlaceUI(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        coordinator_podcast = findViewById(R.id.coordinator_podcast);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        appBarLayout = findViewById(R.id.app_bar);
        headImage = findViewById(R.id.headImage);
        headName=findViewById(R.id.headName);
        pollRecycler=findViewById(R.id.pollRecycler);
        mLManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        pollRecycler.setLayoutManager(mLManager);
        ArrayList<Nominee> aux=new ArrayList<>();
        aux.add(new Nominee("a","Evguenia Medvedeva","https://scontent.flpb1-2.fna.fbcdn.net/v/t1.0-9/12928160_946963058756074_3577779283193955858_n.jpg?_nc_cat=109&_nc_ohc=6ARREG5Nv9kAQkTSrVBqRERquczBKx3hTf9XtKk0EhLRaH1qrBmawdmIQ&_nc_ht=scontent.flpb1-2.fna&oh=a2458183a190a722dcf04f10b5e9aef2&oe=5E6AB138","",65,true));
        aux.add(new Nominee("b","Polina Gagarina","https://vignette.wikia.nocookie.net/eurovisionsongcontests/images/2/2a/Polina_Gagarina.jpg/revision/latest?cb=20190210174057","",25,false));
        aux.add(new Nominee("c","Alina Santander","","https://cdn.redbolivision.tv.bo/files/2017/07/31/alina.jpg",6,false));
        aux.add(new Nominee("d","Alissa Strekosova","","https://i.ytimg.com/vi/T0FwnaBtS5M/maxresdefault.jpg",4,false));
        /*for(Nominee s:product.getOptions_main()){
            if(aux.size()==0)
                aux.add(new Nominee(s.getName(),s.getPhoto(),true));
            else
                aux.add(new Nominee(s.getName(),s.getPhoto(),false));
        }*/
        colorsAdapter=new SelectableAdapter(new SelectableViewHolder.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Nominee item) {
                if(item.getPhoto()!=null&&!item.getPhoto().equals("")){
                    /*Glide.with(context)
                            .load(item.getPhoto())
                            .apply(RequestOptions.centerCropTransform())
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_round_photo_24px))
                            .transition(withCrossFade())
                            .into(photo);*/
                }
            }
        }, aux, false, getApplicationContext());
        pollRecycler.setAdapter(colorsAdapter);
        colorsAdapter.notifyDataSetChanged();
    }
}
