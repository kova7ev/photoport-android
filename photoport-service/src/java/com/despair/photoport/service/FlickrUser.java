/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.despair.photoport.service;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "flickr_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FlickrUser.findAll", query = "SELECT f FROM FlickrUser f"),
    @NamedQuery(name = "FlickrUser.findById", query = "SELECT f FROM FlickrUser f WHERE f.id = :id"),
    @NamedQuery(name = "FlickrUser.findByOauthToken", query = "SELECT f FROM FlickrUser f WHERE f.oauthToken = :oauthToken"),
    @NamedQuery(name = "FlickrUser.findByTokenSecret", query = "SELECT f FROM FlickrUser f WHERE f.tokenSecret = :tokenSecret"),
    @NamedQuery(name = "FlickrUser.findByUsername", query = "SELECT f FROM FlickrUser f WHERE f.username = :username"),
    @NamedQuery(name = "FlickrUser.findByUserId", query = "SELECT f FROM FlickrUser f WHERE f.userId = :userId")})
public class FlickrUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "oauth_token")
    private String oauthToken;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "token_secret")
    private String tokenSecret;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "user_id")
    private String userId;
    @OneToMany(mappedBy = "flickrUserId")
    private Collection<FlickrUserDevice> flickrUserDeviceCollection;

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

    @XmlTransient
    public Collection<FlickrUserDevice> getFlickrUserDeviceCollection() {
        return flickrUserDeviceCollection;
    }

    public void setFlickrUserDeviceCollection(Collection<FlickrUserDevice> flickrUserDeviceCollection) {
        this.flickrUserDeviceCollection = flickrUserDeviceCollection;
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
        return "com.despair.photoport.FlickrUser[ id=" + id + " ]";
    }
    
}
