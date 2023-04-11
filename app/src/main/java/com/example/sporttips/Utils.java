package com.example.sporttips;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public void Toast(Context context,String toast){
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
