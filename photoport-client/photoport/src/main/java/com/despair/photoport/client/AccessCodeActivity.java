package com.despair.photoport.client;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;


@EActivity(R.layout.activity_access_code)
@OptionsMenu(R.menu.access_code)
public class AccessCodeActivity extends ActionBarActivity {

    @ViewById(R.id.accessCode)
    protected TextView accessCode;

    @ViewById(R.id.expiryTime)
    protected TextView expiryTime;

    @Click(R.id.accessCode)
    protected void generateNewAccessCode(){

    }

    @OptionsItem(R.id.action_settings)
    public void settings(MenuItem item) {
        super.onOptionsItemSelected(item);
    }

    @AfterViews
    public void onCreate() {
        Intent intent = getIntent();
        String code = intent.getStringExtra(ProviderApiActivity.EXTRA_ACCESS_CODE);
        String time = intent.getStringExtra(ProviderApiActivity.EXTRA_EXPIRY_TIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));

        accessCode.setText(code);
        expiryTime.setText(String.format("expired: %d:%d" , calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 5));
    }
}
