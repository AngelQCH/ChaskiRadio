package dev.ekeko.chaskiradio;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dev.ekeko.chaskiradio.adapter.PreviewAdapter;
import dev.ekeko.chaskiradio.model.Preview;
import dev.ekeko.chaskiradio.utilities.FirebaseReferences;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Preview> previews = new ArrayList<>();
    RecyclerView previewRecycler;
    GridLayoutManager mLayoutManager;
    PreviewAdapter previewAdapter;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.ROOT_REF);
    AppCompatImageView photoMain;
    AppCompatTextView nameMain,infoMain;
    FrameLayout podcastMain;
    Preview previewMain=new Preview();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlaceUI();
        previewRecycler.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        previewRecycler.setItemAnimator(new DefaultItemAnimator());
        previewRecycler.setLayoutManager(mLayoutManager);
        previewAdapter = new PreviewAdapter(previews, R.layout.podcast_item, this);
        previewRecycler.setAdapter(previewAdapter);
        rootRef.child(FirebaseReferences.PREVIEW_REF).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean primera=true;
                previews.removeAll(previews);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Preview preview = snapshot.getValue(Preview.class);
                    if(primera){
                        previewMain=preview;
                        primera=false;
                        if(preview.getPhoto()!=null&&!preview.getPhoto().equals("")){
                            if(preview.getPhoto().length()>=2){
                                Glide.with(getApplicationContext())
                                        .load(preview.getPhoto())
                                        .apply(RequestOptions.centerCropTransform())
                                        .transition(withCrossFade())
                                        .into(photoMain);
                            }else{
                                switch (Integer.parseInt(preview.getPhoto())){
                                    case 0:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.radio_art));
                                        break;
                                    case 1:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_metronome));
                                        break;
                                    case 2:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_microphone));
                                        break;
                                    case 3:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_clave_sol));
                                        break;
                                    case 4:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_metronome));
                                        break;
                                    case 5:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_microphone));
                                        break;
                                    default:
                                        photoMain.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_clave_sol));
                                        break;
                                }
                            }
                        }else{
                            //Mostrar algo por defecto, por ejemplo una ilustración de radio o de disco o de problemas técnicos
                        }
                        nameMain.setText(preview.getName());
                        infoMain.setText(preview.getDate()+", "+preview.getCounter()+" min");
                    }else{
                        previews.add(preview);   
                    }
                }
                previewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void enlaceUI() {
        podcastMain=findViewById(R.id.podcastMain);
        photoMain=findViewById(R.id.photoMain);
        nameMain=findViewById(R.id.nameMain);
        infoMain=findViewById(R.id.infoMain);
        previewRecycler = findViewById(R.id.previewsRecycler);
        podcastMain.setOnClickListener(this);
    }

    private ArrayList<Preview> filter(ArrayList<Preview> previews, String texto) {
        ArrayList<Preview> listaFiltrada = new ArrayList<>();
        try {
            texto = texto.toLowerCase();
            for (Preview previewi : previews) {
                String textoTotal = previewi.getName().toLowerCase();
                if (textoTotal.contains(texto)) {//si el texto introducido al buscador esta en notaoObten da true
                    listaFiltrada.add(previewi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFiltrada;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.podcastMain:
                Intent intent = new Intent(this, PodcastActivity.class);
                intent.putExtra("previewSel", previewMain);
                startActivity(intent);
                break;
        }
    }
}