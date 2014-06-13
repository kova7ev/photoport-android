package com.despair.photoport.client.utilities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.provider.Settings;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by root on 6/12/14.
 */
public class DeviceInfoHelper {
    public static Context Context;

    public static String getDeviceId(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceId(){
        return  getDeviceId(Context);
    }

    public static String getPrimaryEmail(Context context){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();

        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                return  possibleEmail;
            }
        }

        return "NULL";
    }

    public static String getPrimaryEmail(){
        return getPrimaryEmail(Context);
    }
}
