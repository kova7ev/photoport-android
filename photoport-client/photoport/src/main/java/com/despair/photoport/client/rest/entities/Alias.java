package com.despair.photoport.client.rest.entities;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author root
 */
public class Alias implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("generatedCode")
    private String generatedCode;
    @JsonProperty("timestamp")
    private BigInteger timestamp;
    @JsonProperty("providerId")
    private Provider providerId;

    public Alias() {
    }

    public Alias(Integer id) {
        this.id = id;
    }

    public Alias(Integer id, String generatedCode, BigInteger timestamp) {
        this.id = id;
        this.generatedCode = generatedCode;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
    }

    public Provider getProviderId() {
        return providerId;
    }

    public void setProviderId(Provider providerId) {
        this.providerId = providerId;
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
        if (!(object instanceof Alias)) {
            return false;
        }
        Alias other = (Alias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("com.despair.photoport.Alias[ id=%s, code=%s, timestamp=%d]", id, generatedCode, Long.parseLong(timestamp.toString()));
    }
}