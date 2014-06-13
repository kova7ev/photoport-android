package com.despair.photoport.client.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.despair.photoport.client.FlickrAuthorizationActivity;
import com.despair.photoport.client.ProviderApiActivity;
import com.despair.photoport.client.flickr.tasks.FlickrKey;
import com.despair.photoport.client.flickr.tasks.OAuthTask;
import com.despair.photoport.client.rest.ServiceApi;
import com.despair.photoport.client.rest.entities.FlickrUser;
import com.despair.photoport.client.rest.entities.Provider;
import com.despair.photoport.client.utilities.DeviceInfoHelper;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.people.User;

import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.annotations.rest.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by root on 6/12/14.
 */
public class RetrieveFlickrTask extends AsyncTask<Void, Void, OAuth> {

    private static final Logger logger = LoggerFactory.getLogger(RetrieveFlickrTask.class);
    private Context context;

    public RetrieveFlickrTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected OAuth doInBackground(Void... params) {
        ProviderApiActivity activity = (ProviderApiActivity) context;
        String hash = DeviceInfoHelper.getDeviceId(context);
        FlickrUser flickrUser = activity.getServiceApi().getFlickr(hash);
        OAuth oauth = null;

        if (flickrUser != null) {

            oauth = new OAuth();
            User user = new User();
            user.setUsername(flickrUser.getUsername());
            user.setId(flickrUser.getUserId());
            oauth.setUser(user);

            OAuthToken oauthToken = new OAuthToken();
            oauth.setToken(oauthToken);
            oauthToken.setOauthToken(flickrUser.getOauthToken());
            oauthToken.setOauthTokenSecret(flickrUser.getTokenSecret());

            logger.debug("Retrieved token from service: oauth token={}, and token secret={}", flickrUser.getOauthToken(), flickrUser.getTokenSecret());
        } else {
            SharedPreferences settings = activity.getSharedPreferences(FlickrAuthorizationActivity.PREFS_NAME, Context.MODE_PRIVATE);

            String oauthTokenString = settings.getString(FlickrAuthorizationActivity.KEY_OAUTH_TOKEN, null);
            String tokenSecret = settings.getString(FlickrAuthorizationActivity.KEY_TOKEN_SECRET, null);

            if (oauthTokenString == null && tokenSecret == null) {
                logger.warn("No oauth token retrieved");
                return null;
            }

            oauth = new OAuth();

            String userName = settings.getString(FlickrAuthorizationActivity.KEY_USER_NAME, null);
            String userId = settings.getString(FlickrAuthorizationActivity.KEY_USER_ID, null);

            if (userId != null) {
                User user = new User();
                user.setUsername(userName);
                user.setId(userId);
                oauth.setUser(user);
            }

            OAuthToken oauthToken = new OAuthToken();
            oauth.setToken(oauthToken);
            oauthToken.setOauthToken(oauthTokenString);
            oauthToken.setOauthTokenSecret(tokenSecret);

            logger.debug("Retrieved token from preference store: oauth token={}, and token secret={}", oauthTokenString, tokenSecret);
        }

        activity.setOAuth(oauth);
        return oauth;
    }

    @Override
    protected void onPostExecute(OAuth oauth) {
        if (oauth == null || oauth.getUser() == null) {
            OAuthTask task = new OAuthTask(context);
            task.execute();
        }
    }
}
