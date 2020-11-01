package dev.ekeko.chaskiradio;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.ekeko.chaskiradio.adapter.ActivityAdapter;
import dev.ekeko.chaskiradio.adapter.StaffAdapter;
import dev.ekeko.chaskiradio.model.Podcast;
import dev.ekeko.chaskiradio.model.Preview;
import dev.ekeko.chaskiradio.model.Staff;
import dev.ekeko.chaskiradio.utilities.FirebaseReferences;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

/**
 * A fullscreen activity to play audio or video streams.
 */
public class PodcastActivity extends AppCompatActivity implements View.OnClickListener{

    private PlayerControlView playerView;
    private SimpleExoPlayer player;

    CoordinatorLayout coordinator_podcast;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ActionBar ab;
    ViewPager preparacion, presentacion;
    //StepAdapter preparationAdapter, presentationAdapter;
    AppBarLayout appBarLayout;
    FloatingActionButton share, save;
    AppCompatTextView title, info, description;
    Preview podcastPreview;
    AppCompatImageView headImage;

    AppCompatTextView difficulty, time, number, cost;
    AppCompatTextView titlePresentation, titlePreparation;
    Intent intent;
    Animation animation;
    LottieAnimationView favorite_anim, preparacion_anim, presentacion_anim;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.ROOT_REF);
    DatabaseReference previewRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.PREVIEW_REF);

    DatabaseReference staffsRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.BIDS_REF);
    DatabaseReference guardadosRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.SAVE_REF);
    DatabaseReference favoritosRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.FAVORITES_REF);

    Podcast podcast;
    Preview preview;
    String nombre = "KC";
    int precio=0;
    StaffAdapter staffAdapter;
    ActivityAdapter activityAdapter;
    RecyclerView staffRecycler,activitiesRecycler;
    LinearLayoutManager mLayoutManager,activitiesLayoutManager;
    ArrayList<Staff> staffs=new ArrayList<>();
    ArrayList<Preview> activities=new ArrayList<>();
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        podcastPreview = (Preview) getIntent().getSerializableExtra("previewSel");
        enlaceUI();
        //initializePlayer();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -appBarLayout.getTotalScrollRange() + toolbar.getHeight()) {
                    toolbar.setTitle(podcastPreview.getName());
                    /*if(precio!=0)
                        toolbar.setSubtitle("Bs "+precio);*/
                } else {
                    toolbar.setTitle("");
                    //toolbar.setSubtitle("");
                }
            }
        });
        rootRef.child(FirebaseReferences.PODCAST_REF).child(podcastPreview.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                podcast = dataSnapshot.getValue(Podcast.class);
                if (podcast != null)
                    cargarPodcast(podcast);
                else {
                    cargarPreview();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //cargarPujas(podcastPreview.getId());
    }
    public void enlaceUI(){
        playerView = findViewById(R.id.video_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        coordinator_podcast = findViewById(R.id.coordinator_podcast);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        appBarLayout = findViewById(R.id.app_bar);
        headImage = findViewById(R.id.headImage);
        title=findViewById(R.id.title);
        info=findViewById(R.id.info);
        description=findViewById(R.id.description);
        staffRecycler=findViewById(R.id.staffRecycler);
        activitiesRecycler=findViewById(R.id.activitiesRecycler);
    }

    public void cargarPujas(String idPodcast){
        staffRecycler.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        staffRecycler.setItemAnimator(new DefaultItemAnimator());
        staffRecycler.setLayoutManager(mLayoutManager);
        staffAdapter=new StaffAdapter(staffs,R.layout.staff_item,this);
        staffRecycler.setAdapter(staffAdapter);

        staffsRef.child(idPodcast).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                staffs.removeAll(staffs);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Staff staff=snapshot.getValue(Staff.class);
                    //problemas.add(problema);
                    staffs.add(staff);//Añade el último elemento al inicio y recorre el resto a la derecha
                }
                staffAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cargarPreview() {
        rootRef.child(FirebaseReferences.PREVIEW_REF).child(podcastPreview.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preview = dataSnapshot.getValue(Preview.class);
                if (preview != null) {
                    cargarImagen(preview.getPhoto());
                    cargarPreview(preview);
                } else {
                    podcast = new Podcast();
                    podcast.setPhoto("https://images.pexels.com/photos/1070880/pexels-photo-1070880.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
                    podcast.setName("Proximamente");
                    Glide.with(getApplicationContext())
                            .load("https://images.pexels.com/photos/1070880/pexels-photo-1070880.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                            .apply(RequestOptions.centerCropTransform())
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_round_photo_24px))
                            .into(headImage);
                    //Toast.makeText(PodcastActivity.this, "No disponible por ahora", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cargarPreview(Preview preview) {
        nombre = preview.getName();
        cargarImagen(preview.getPhoto());
        title.setText(preview.getName() + "\n(Próximamente)");
        info.setText(preview.getDate()+", "+preview.getCounter()+" min");
        if(preview.getDescription()!=null&&!preview.getDescription().equals(""))
            description.setText(preview.getDescription());
        else
            description.setText("Más información en breve.");
        //precio=preview.getPrice();
        podcast = new Podcast();
        podcast.setPhoto(preview.getPhoto());
        podcast.setName(preview.getName() + "\n(Próximamente)");
        headImage.setOnClickListener(this);
        toolbar.setOnClickListener(this);
    }

    public void cargarPodcast(Podcast podcast) {
        nombre = podcast.getName();
        cargarImagen(podcast.getPhoto());
        title.setText(podcast.getName());
        info.setText(podcast.getDate()+", "+podcast.getCounter()+" min");//Para el futuro recuerda habilitar AHORA EN DIRECTO para lo que es en directo
        if(podcast.getDescription()!=null&&!podcast.getDescription().equals(""))
            description.setText(podcast.getDescription());
        else
            description.setText("Más información en breve.");
        headImage.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        if(podcast.getStaff()!=null&&podcast.getStaff().size()>0){
            staffRecycler.setVisibility(View.VISIBLE);
            cargarStaff();
        }
        else
            staffRecycler.setVisibility(View.GONE);

        if(podcast.getActivities()!=null&&podcast.getActivities().size()>0){
            activitiesRecycler.setVisibility(View.VISIBLE);
            cargarAcitivities();
        }
        else
            activitiesRecycler.setVisibility(View.GONE);
    }
    public void cargarStaff(){
        staffs.removeAll(staffs);
        staffs=podcast.getStaff();

        staffRecycler.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        staffRecycler.setItemAnimator(new DefaultItemAnimator());
        staffRecycler.setLayoutManager(mLayoutManager);
        staffAdapter=new StaffAdapter(staffs,R.layout.staff_item,this);
        staffRecycler.setAdapter(staffAdapter);
        //staffAdapter.notifyDataSetChanged();
    }
    public void cargarAcitivities(){
        activities.removeAll(activities);
        activities=podcast.getActivities();

        activitiesRecycler.setHasFixedSize(true);
        activitiesLayoutManager=new LinearLayoutManager(this);
        activitiesLayoutManager.setOrientation(RecyclerView.VERTICAL);
        activitiesRecycler.setItemAnimator(new DefaultItemAnimator());
        activitiesRecycler.setLayoutManager(activitiesLayoutManager);
        activityAdapter=new ActivityAdapter(activities,R.layout.activity_item,this);
        activitiesRecycler.setAdapter(activityAdapter);
        //staffAdapter.notifyDataSetChanged();
    }
    public void cargarImagen(String fotoUrl) {
        //Cargamos la imagen y los colores de acuerdo a la imagen
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(fotoUrl)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_round_photo_24px))
                .transition(withCrossFade())
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
//                                  Toast.makeText(cxt,getResources().getString(R.string.unexpected_error_occurred_try_again),Toast.LENGTH_SHORT).show();
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                  Palette.from(resource).generate(
                                          new Palette.PaletteAsyncListener() {
                                              @SuppressWarnings("ResourceType")
                                              @Override
                                              public void onGenerated(Palette palette) {

                                                  int vibrantColor = palette
                                                          .getVibrantColor(R.color.colorPrimary);
                                                  appBarLayout.setBackgroundColor(vibrantColor);
                                                  collapsingToolbarLayout
                                                          .setStatusBarScrimColor(vibrantColor);
                                                  collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                                                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                      getWindow().setStatusBarColor(getDarkColor(vibrantColor, 0.8f));
                                                  }
                                                  //title.setTextColor(vibrantColor);
                                              }
                                          });
                                  return false;
                              }
                          }
                )
                .into(headImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            /*case R.id.action_save:

                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                intentImagen();
                break;
            case R.id.headImage:
                intentImagen();
                break;
        }

    }
    public void intentImagen(){/*
        intent = new Intent(this, PhotosActivity.class);
        intent.putExtra("position", 0);
        if(podcast.getPhotos()==null){
            ArrayList<String> fotos=new ArrayList<>();
            fotos.add(podcast.getPhoto());
            intent.putExtra("fotos", fotos);
        }else {
            intent.putExtra("fotos", podcast.getPhotos());
        }
        intent.putExtra("title",podcast.getName());
        startActivity(intent);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_podcast, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static int getDarkColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    private void initializePlayer() {
        //"http://server-23.stream-server.nl:8438"
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        Uri uri = Uri.parse(podcastPreview.getUrlStreaming());
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }
    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

}
