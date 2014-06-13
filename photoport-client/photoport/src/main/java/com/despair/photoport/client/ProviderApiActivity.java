package com.despair.photoport.client;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;

import com.despair.photoport.client.dialogs.UncoupleDialog;
import com.despair.photoport.client.flickr.tasks.FlickrKey;
import com.despair.photoport.client.listeners.SimpleDialogListener;
import com.despair.photoport.client.rest.ServiceApi;
import com.despair.photoport.client.rest.entities.Alias;
import com.despair.photoport.client.rest.entities.Device;
import com.despair.photoport.client.rest.entities.FlickrUser;
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


@EActivity(R.layout.activity_provider_api)
@OptionsMenu(R.menu.provider_api)
public class ProviderApiActivity extends FlickrAuthorizationActivity implements SimpleDialogListener {

    @RestService
    protected ServiceApi serviceApi;

    @ViewById(R.id.registerButton)
    protected Button registerButton;

    @ViewById(R.id.generateAccessCodeButton)
    protected Button generateAccessCodeButton;

    @ViewById(R.id.uncoupleButton)
    protected Button uncoupleButton;

    @ViewById(R.id.updateOrAddFlickrUserButton)
    protected Button updateOrAddFlickrUserButton;

    @ViewById(R.id.getFlickrAccountButton)
    protected Button getFlickrAccountButton;

    private static final Logger logger = LoggerFactory.getLogger(ProviderApiActivity.class);
    public static final String EXTRA_ACCESS_CODE = "com.despair.photoport.client.ACCESS_CODE";
    public static final String EXTRA_EXPIRY_TIME = "com.despair.photoport.client.EXPIRY_TIME";
    private boolean registered = false;

    @OptionsItem(R.id.action_settings)
    public void settings(MenuItem item) {
        super.onOptionsItemSelected(item);
    }

    @Click(R.id.registerButton)
    @Background
    protected void register() {
        DeviceInfoHelper.Context = this;
        Device device = serviceApi.register(DeviceInfoHelper.getDeviceId(), DeviceInfoHelper.getPrimaryEmail(), "Provider");

        registered = true;
        logger.warn(device.toString());
    }

    @Click(R.id.generateAccessCodeButton)
    @Background
    protected void generateAccessCode() {
        if (!registered) {
            logger.warn("Device not registered.");
        }

        Intent intent = new Intent(this, AccessCodeActivity_.class);

        DeviceInfoHelper.Context = this;
        String hash = DeviceInfoHelper.getDeviceId();
        Alias alias = serviceApi.generateAccessCode(hash);

        logger.warn(alias.toString());

        intent.putExtra(EXTRA_ACCESS_CODE, alias.getGeneratedCode());
        intent.putExtra(EXTRA_EXPIRY_TIME, alias.getTimestamp().toString());
        startActivity(intent);
    }

    @Click(R.id.uncoupleButton)
    protected void uncouple() {
        UncoupleDialog dialog = new UncoupleDialog();
        dialog.message = "Unlink all connected devices?";
        dialog.addDialogListener(this);
        dialog.show(getSupportFragmentManager(), "Uncouple dialog");
    }

    @Override
    @Background
    public void OnPositiveButtonClick() {
        DeviceInfoHelper.Context = this;
        serviceApi.uncouple(DeviceInfoHelper.getDeviceId());
    }

    @Override
    public void OnNegativeButtonClick() {

    }

    @Click(R.id.updateOrAddFlickrUserButton)
    protected void updateFlickrUser() {
        authorize();
    }

    /*
    @Override
    @Background
    public FlickrKey retrieveAccountKey() {
        DeviceInfoHelper.Context = this;
        FlickrUser user = serviceApi.getFlickr(DeviceInfoHelper.getDeviceId());

        if (user != null) {
            return new FlickrKey(user.getOauthToken(), user.getTokenSecret(), user.getUsername(), user.getUserId());
        }
    }*/

    @Override
    @Background
    public void saveAccountKey(FlickrKey key) {
        DeviceInfoHelper.Context = this;

        String oauthToken = (key.getOauthToken() == null || key.getOauthToken().equals("")) ? "NULL" : key.getOauthToken();
        String tokenSecret = key.getTokenSecret();
        String username = (key.getUsername() == null || key.getUsername().equals("")) ? "NULL" : key.getUsername();
        String userId = (key.getUserId() == null || key.getUserId().equals("")) ? "NULL" : key.getUserId();

        serviceApi.updateOrAddFlickrUser(DeviceInfoHelper.getDeviceId(), oauthToken, tokenSecret, username, userId);
    }

    public ServiceApi getServiceApi(){
        return serviceApi;
    }

    @Click(R.id.getFlickrAccountButton)
    @Background
    public void retrieveFlickrAccount(){
        FlickrUser flickrUser = serviceApi.getFlickr(DeviceInfoHelper.getDeviceId(this));
        logger.warn(flickrUser.toString());
    }
}