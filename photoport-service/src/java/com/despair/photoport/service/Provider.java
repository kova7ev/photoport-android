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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "provider")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provider.findAll", query = "SELECT p FROM Provider p"),
    @NamedQuery(name = "Provider.findById", query = "SELECT p FROM Provider p WHERE p.id = :id")})
public class Provider implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "providerId")
    private Collection<Alias> aliasCollection;
    @OneToMany(mappedBy = "providerId")
    private Collection<LinkedDevices> linkedDevicesCollection;
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    @ManyToOne
    private Device providerId;

    public Provider() {
    }

    public Provider(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<Alias> getAliasCollection() {
        return aliasCollection;
    }

    public void setAliasCollection(Collection<Alias> aliasCollection) {
        this.aliasCollection = aliasCollection;
    }

    @XmlTransient
    public Collection<LinkedDevices> getLinkedDevicesCollection() {
        return linkedDevicesCollection;
    }

    public void setLinkedDevicesCollection(Collection<LinkedDevices> linkedDevicesCollection) {
        this.linkedDevicesCollection = linkedDevicesCollection;
    }

    public Device getProviderId() {
        return providerId;
    }

    public void setProviderId(Device providerId) {
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
        if (!(object instanceof Provider)) {
            return false;
        }
        Provider other = (Provider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.despair.photoport.Provider[ id=" + id + " ]";
    }
    
}
