package com.example.photographer.Tools;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts {

    Context context;
    public Fonts(Context context){
        this.context = context;
    }

    public Typeface getCharmonmanFont(){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Charmonman-Bold.ttf");
    }

    public Typeface getRedressedFont(){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Redressed-Regular.ttf");
    }

    public Typeface getComfortaaFont(){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Comfortaa-VariableFont_wght.ttf");
    }
    public Typeface getJostFont(){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Jost.ttf");
    }

    public Typeface getUbuntuFont(){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Ubuntu-Regular.ttf");
    }
}
