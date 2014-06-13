package com.despair.photoport.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.despair.photoport.client.async.RetrieveFlickrTask;
import com.despair.photoport.client.flickr.tasks.FlickrKey;
import com.despair.photoport.client.flickr.tasks.GetOAuthTokenTask;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.people.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


public abstract class FlickrAuthorizationActivity extends ActionBarActivity {

    public static final String CALLBACK_SCHEME = "photoport-oauth";
    public static final String PREFS_NAME = "photoport-pref";
    public static final String KEY_OAUTH_TOKEN = "photoport-oauthToken";
    public static final String KEY_TOKEN_SECRET = "photoport-tokenSecret";
    public static final String KEY_USER_NAME = "photoport-userName";
    public static final String KEY_USER_ID = "photoport-userId";

    private static final Logger logger = LoggerFactory.getLogger(FlickrAuthorizationActivity.class);
    private OAuth oauth;

    public abstract void saveAccountKey(FlickrKey key);

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String scheme = intent.getScheme();
        OAuth savedToken = getOAuth();
        if (CALLBACK_SCHEME.equals(scheme) && (savedToken == null || savedToken.getUser() == null)) {
            Uri uri = intent.getData();
            String query = uri.getQuery();

            logger.debug("Returned Query: {}", query);

            String[] data = query.split("&");
            if (data != null && data.length == 2) {
                String oauthToken = data[0].substring(data[0].indexOf("=") + 1);
                String oauthVerifier = data[1].substring(data[1].indexOf("=") + 1);

                logger.debug("OAuth Token: {}; OAuth Verifier: {}", oauthToken, oauthVerifier);

                OAuth oauth = getOAuth();
                if (oauth != null && oauth.getToken() != null && oauth.getToken().getOauthTokenSecret() != null) {
                    GetOAuthTokenTask task = new GetOAuthTokenTask(this);
                    task.execute(oauthToken, oauth.getToken().getOauthTokenSecret(), oauthVerifier);
                }
            }
        }
    }

    public void authorize() {
        new RetrieveFlickrTask(this).execute();
    }

    public void onOAuthDone(OAuth result) {
        if (result == null) {
            Toast.makeText(this, "Authorization failed", Toast.LENGTH_LONG).show();
        } else {
            User user = result.getUser();
            OAuthToken token = result.getToken();
            if (user == null || user.getId() == null || token == null
                    || token.getOauthToken() == null
                    || token.getOauthTokenSecret() == null) {
                Toast.makeText(this, "Authorization failed", Toast.LENGTH_LONG).show();
                return;
            }
            String message = String.format(Locale.US, "Authorization Succeed: user=%s, userId=%s, oauthToken=%s, tokenSecret=%s",
                    user.getUsername(), user.getId(), token.getOauthToken(), token.getOauthTokenSecret());
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            saveOAuthToken(user.getUsername(), user.getId(), token.getOauthToken(), token.getOauthTokenSecret());
        }
    }

    public void saveOAuthToken(String userName, String userId, String token, String tokenSecret) {
        logger.debug("Saving userName=%s, userId=%s, oauth token={}, and token secret={}", new String[]{userName, userId, token, tokenSecret}); //$NON-NLS-1$
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_OAUTH_TOKEN, token);
        editor.putString(KEY_TOKEN_SECRET, tokenSecret);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_ID, userId);
        editor.commit();

        saveAccountKey(new FlickrKey(token, tokenSecret, userName, userId));
    }

    public OAuth getOAuth(){
        return oauth;
    }

    public void setOAuth(OAuth oauth){
        this.oauth = oauth;
    }
}
