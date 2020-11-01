package dev.ekeko.chaskiradio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import dev.ekeko.chaskiradio.PodcastActivity;
import dev.ekeko.chaskiradio.R;
import dev.ekeko.chaskiradio.model.Staff;
import dev.ekeko.chaskiradio.utilities.GlobalFunctions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private ArrayList<Staff> staffs;
    private int resource;
    private Context context;
    //MediaPlayer mediaPlayer = new MediaPlayer();
    public StaffAdapter(ArrayList<Staff> staffs, int resource, Context context) {
        this.staffs = staffs;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public StaffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new StaffViewHolder(view);
    }
    @Override
    public void onBindViewHolder(StaffViewHolder holder, final int pos) {
        Staff staff = staffs.get(pos);
        //holder.cardView.setCardBackgroundColor(Color.parseColor(staff.getColor()));
        Glide.with(context)
                .load(staff.getPhoto())
                .apply(RequestOptions.circleCropTransform())
                .transition(withCrossFade())
                .into(holder.photo);
        holder.name.setText(staff.getName());
    }

    @Override
    public int getItemCount() {
        return staffs.size();
    }

    public class StaffViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView photo;
        private AppCompatTextView name;

        public StaffViewHolder(View itemView) {
            super(itemView);
            photo= itemView.findViewById(R.id.photo);
            name= itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Staff staffSel = staffs.get(getAdapterPosition());
                    detalleStaff(staffSel);
                }
            });
        }
    }
    public void setFilter(ArrayList<Staff> staffs){
        this.staffs =new ArrayList<>();
        this.staffs.addAll(staffs);
        notifyDataSetChanged();
    }
    public void detalleStaff(Staff staff){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.staff_dialog2, null);//le enviamos nuestro propio dialog
        AppCompatTextView name,position,description;
        final AppCompatImageView photo;
        AppCompatImageButton facebook,instagram,linkedin,twitter,whatsapp,youtube;
        facebook=dialogView.findViewById(R.id.facebook);
        instagram=dialogView.findViewById(R.id.instagram);
        linkedin=dialogView.findViewById(R.id.linkedin);
        twitter=dialogView.findViewById(R.id.twitter);
        whatsapp=dialogView.findViewById(R.id.whatsapp);
        youtube=dialogView.findViewById(R.id.youtube);
        //Depende de si nos dan el identificador de la página hacemos esto:
        /*Tenemos que pasar una cadena primero con el identificador numérico luego una coma y el identificador textual, y en la siguiente coma un 0 o 1 para saber
        si es un perfil o una página
        Ej, 2129362130617681,GastronomiconApp,1

        * */
        facebook.setOnClickListener(v -> GlobalFunctions.openFacebook(context,"GastronomiconApp"));
        instagram.setOnClickListener(v -> GlobalFunctions.openInstagram(context,"jmedvedevaj"));
        linkedin.setOnClickListener(v -> GlobalFunctions.openLinkedin(context,"https://bo.linkedin.com/in/lizarragadev/%7Bcountry%3Dus%2C+language%3Den%7D?trk=people-guest_people_search-card"));
        twitter.setOnClickListener(v -> GlobalFunctions.openTwitter(context,"lizarragadev"));
        whatsapp.setOnClickListener(v -> GlobalFunctions.openWhatsApp(context,"59173745638"));
        youtube.setOnClickListener(v -> GlobalFunctions.openYouTube(context,"https://www.youtube.com/user/franserox1"));
        name=dialogView.findViewById(R.id.nameDialog);
        position=dialogView.findViewById(R.id.positionDialog);
        description=dialogView.findViewById(R.id.descriptionDialog);
        photo=dialogView.findViewById(R.id.photoDialog);
        Glide.with(context)
                .load(staff.getPhoto())
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_round_photo_24px))
                .transition(withCrossFade())
                .into(photo);
        name.setText(staff.getName());
        position.setText(staff.getPosition());
        description.setText(staff.getDescription());
        dialogBuilder.setView(dialogView);
        //dialogBuilder.setPositiveButton("Agregar al carrito",null);
        //dialogBuilder.setPositiveButtonIcon(context.getResources().getDrawable(R.drawable.ic_round_add_shopping_cart_24px));
        final AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
