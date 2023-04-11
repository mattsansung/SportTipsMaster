package com.example.sporttips;

import android.content.Context;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPass {
    private Context context;
    private static String LETTER_DIGIT_REGEX = "^[a-z0-9A-Z]+$";
    public CheckPass(Context context){
        this.context = context;
    }
    public boolean checkPassword(String password){
        Utils utils = new Utils();
        if (password.length()<8){
            utils.Toast(context,"the length of password < 8");
            return false;
        }else if (isLetterandDigit(password)){
            return true;
        }else {
            utils.Toast(context,"Password should contain numbers and letters");
            return false;
        }
    }
    public  boolean isLetterandDigit(String str) {
        String regex1 = ".*[a-zA-z].*";
        boolean result1 = str.matches(regex1);
        String regex2 = ".*[0-9].*";
        boolean result2 = str.matches(regex2);
        return result1&&result2;
    }

}
