package com.despair.photoport.client;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Button;

import com.despair.photoport.client.rest.ServiceApi;
import com.despair.photoport.client.rest.entities.Device;
import com.despair.photoport.client.rest.entities.FlickrUser;
import com.despair.photoport.client.rest.entities.Provider;
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


@EActivity(R.layout.activity_receiver_api)
@OptionsMenu(R.menu.reciver_api)
public class ReceiverApiActivity extends ActionBarActivity {

    @RestService
    protected ServiceApi serviceApi;

    @ViewById(R.id.registerButton)
    protected Button registerButton;

    @ViewById(R.id.coupleButton)
    protected Button coupleButton;

    @ViewById(R.id.uncoupleButton)
    protected Button uncoupleButton;

    @ViewById(R.id.getProviderButton)
    protected Button getProviderButton;

    @ViewById(R.id.getFlickrAccountButton)
    protected Button getFlickrAccount;

    private static final Logger logger = LoggerFactory.getLogger(ProviderApiActivity.class);
    private boolean registered = false;


    @OptionsItem(R.id.action_settings)
    public void settings(MenuItem item) {
        super.onOptionsItemSelected(item);
    }

    @Click(R.id.registerButton)
    @Background
    protected void register() {
        DeviceInfoHelper.Context = this;
        Device device = serviceApi.register(DeviceInfoHelper.getDeviceId(), DeviceInfoHelper.getPrimaryEmail(), "Receiver");

        registered = true;
        logger.warn(device.toString());
    }

    @Click(R.id.coupleButton)
    protected void couple() {
        Intent intent = new Intent(this, AccessCodeChecker_.class);
        startActivity(intent);
    }

    @Click(R.id.uncoupleButton)
    @Background
    protected void uncouple() {
        serviceApi.uncouple(DeviceInfoHelper.getDeviceId(this));
        logger.warn("uncouple");
    }

    @Click(R.id.getProviderButton)
    @Background
    protected void retrieveProvider() {
        Provider provider = serviceApi.getProvider(DeviceInfoHelper.getDeviceId(this));
        logger.warn(provider == null ? "provider == NULL" : provider.toString());
    }

    @Click(R.id.getFlickrAccount)
    @Background
    protected void retrieveFlickrAccount() {
        FlickrUser flickrUser = serviceApi.getFlickr(DeviceInfoHelper.getDeviceId(this));

        logger.warn("retrieveFlickrAccount");
        if (flickrUser == null) {
            logger.warn("Flickr account not found");
        } else {
            logger.warn(flickrUser.toString());
        }
    }

}