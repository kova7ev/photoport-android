package com.despair.photoport.client.flickr.tasks;

/**
 * Created by root on 6/12/14.
 */
public class FlickrKey {
    private String oauthToken;
    private String tokenSecret;
    private String username;
    private String userId;

    public FlickrKey(String oauthToken, String tokenSecret, String username, String userId) {
        this.oauthToken = oauthToken;
        this.tokenSecret = tokenSecret;
        this.username = username;
        this.userId = userId;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
