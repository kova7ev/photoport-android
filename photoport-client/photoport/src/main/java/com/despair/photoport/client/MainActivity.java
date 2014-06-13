package com.despair.photoport.client;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends ActionBarActivity {

    @ViewById(R.id.providerButton)
    protected Button providerButton;

    @ViewById(R.id.receiverButton)
    protected Button receiverButton;

    @OptionsItem(R.id.action_settings)
    public void settings(MenuItem item) {
        super.onOptionsItemSelected(item);
    }

    @Click(R.id.providerButton)
    protected void providerClick(){
        Intent intent = new Intent(this, ProviderApiActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.receiverButton)
    protected void receiverClick(){
        Intent intent = new Intent(this, ReceiverApiActivity_.class);
        startActivity(intent);
    }
}