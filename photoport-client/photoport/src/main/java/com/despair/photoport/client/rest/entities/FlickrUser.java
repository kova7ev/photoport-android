package com.despair.photoport.client.rest.entities;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class FlickrUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("oauthToken")
    private String oauthToken;
    @JsonProperty("tokenSecret")
    private String tokenSecret;
    @JsonProperty("username")
    private String username;
    @JsonProperty("userId")
    private String userId;

    public FlickrUser() {
    }

    public FlickrUser(Integer id) {
        this.id = id;
    }

    public FlickrUser(Integer id, String oauthToken, String tokenSecret, String username, String userId) {
        this.id = id;
        this.oauthToken = oauthToken;
        this.tokenSecret = tokenSecret;
        this.username = username;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlickrUser)) {
            return false;
        }
        FlickrUser other = (FlickrUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("com.despair.photoport.FlickrUser[ id=%s, oath_token=%s, secret_token=%s, username=%s, userid=%s",
                id, oauthToken, tokenSecret, username, userId);
    }
    
}
