package com.despair.photoport.client;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.despair.photoport.client.dialogs.UncoupleDialog;
import com.despair.photoport.client.rest.ServiceApi;
import com.despair.photoport.client.rest.entities.LinkedDevices;
import com.despair.photoport.client.utilities.DeviceInfoHelper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@EActivity(R.layout.activity_access_code_checker)
@OptionsMenu(R.menu.access_code_checker)
public class AccessCodeChecker extends ActionBarActivity {

    @RestService
    protected ServiceApi serviceApi;

    @ViewById(R.id.accessCode)
    protected EditText accessCode;

    @ViewById(R.id.message)
    protected TextView message;

    @ViewById(R.id.submitButton)
    protected Button submit;

    private static final Logger logger = LoggerFactory.getLogger(AccessCodeChecker.class);

    @OptionsItem(R.id.action_settings)
    public void settings(MenuItem item) {
        super.onOptionsItemSelected(item);
    }

    @Click(R.id.submitButton)
    @Background
    protected void submit(){
        String code = (accessCode.getText().toString().trim().equals("")) ? "NULL" : accessCode.getText().toString();
        String hash = DeviceInfoHelper.getDeviceId(this);

        LinkedDevices link = serviceApi.couple(code, hash);
        logger.warn(link == null ? "NULL" : link.getProviderId().getProviderId().getHash());


        if (link == null){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    message.setVisibility(View.VISIBLE);
                }
            });
        }else{
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    message.setVisibility(View.INVISIBLE);
                }
            });
            NavUtils.navigateUpFromSameTask(this);
        }
    }
}
