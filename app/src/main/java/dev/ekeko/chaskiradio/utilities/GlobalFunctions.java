package dev.ekeko.chaskiradio.utilities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

public class GlobalFunctions {
    //Ahora métodos para redes sociales del Staff

    public static void openFacebook(Context context,String user) {//este método abre nuestra página de facebook
        try{
            Intent faceIntent= getOpenFacebookIntent(context,user);
            context.startActivity(faceIntent);
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+user+"/")));
        }

    }
    public static Intent getOpenFacebookIntent(Context context,String user){
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/2129362130617681"));
            //return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100006786039250")); Aasí sería para abrir un perfil
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+user+"/"));
        }
    }
    public static void openYouTube (Context context, String canal){
        Intent intent = new  Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.google.android.youtube");
        intent.setData(Uri.parse(canal));
        context.startActivity(intent);
    }
    public static void openLinkedin(Context context, String perfil){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(perfil));
            intent.setPackage("com.linkedin.android");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(perfil)));
        }
    }
    public static void openInstagram(Context context, String link){
        Uri uri = Uri.parse("http://instagram.com/_u/"+link);
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");

        if (isIntentAvailable(context, insta)){
            context.startActivity(insta);
        } else{
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+link)));
        }
    }
    private static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void openWhatsApp(Context context, String numero) {//Ej.: 59173745638
        try {
            String url="https://wa.me/"+numero+"?text=";
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Ninguna aplicación puede manejar esta solicitud. Instale un navegador web o consulte su URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public static void openTwitter(Context context,String user) {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+user));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            return intent;
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+user));
        }
        context.startActivity(intent);
    }
}
