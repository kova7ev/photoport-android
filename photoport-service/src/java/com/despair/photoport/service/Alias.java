/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.despair.photoport.service;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "alias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alias.findAll", query = "SELECT a FROM Alias a"),
    @NamedQuery(name = "Alias.findById", query = "SELECT a FROM Alias a WHERE a.id = :id"),
    @NamedQuery(name = "Alias.findByGeneratedCode", query = "SELECT a FROM Alias a WHERE a.generatedCode = :generatedCode"),
    @NamedQuery(name = "Alias.findByTimestamp", query = "SELECT a FROM Alias a WHERE a.timestamp = :timestamp")})
public class Alias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "generated_code")
    private String generatedCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private BigInteger timestamp;
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    @ManyToOne
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
        return "com.despair.photoport.Alias[ id=" + id + " ]";
    }
    
}
