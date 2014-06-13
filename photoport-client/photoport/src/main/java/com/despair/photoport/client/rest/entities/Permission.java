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
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("storageAccess")
    private StorageAccess storageAccess;
    @JsonProperty("permissionType")
    private PermissionType permissionType;
    @JsonProperty("device_id")
    private Device deviceId;

    public Permission() {
    }

    public Permission(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StorageAccess getStorageAccess() {
        return storageAccess;
    }

    public void setStorageAccess(StorageAccess storageAccess) {
        this.storageAccess = storageAccess;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
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
        if (!(object instanceof Permission)) {
            return false;
        }
        Permission other = (Permission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.despair.photoport.Permission[ id=" + id + " ]";
    }
    
}
