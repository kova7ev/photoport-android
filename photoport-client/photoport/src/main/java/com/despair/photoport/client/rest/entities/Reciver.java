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
public class Reciver implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("reciverId")
    private Device reciverId;

    public Reciver() {
    }

    public Reciver(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Device getReciverId() {
        return reciverId;
    }

    public void setReciverId(Device reciverId) {
        this.reciverId = reciverId;
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
        if (!(object instanceof Reciver)) {
            return false;
        }
        Reciver other = (Reciver) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.despair.photoport.Reciver[ id=" + id + " ]";
    }
    
}
