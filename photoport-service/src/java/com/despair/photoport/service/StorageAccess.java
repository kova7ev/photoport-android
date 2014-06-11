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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "storage_access")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StorageAccess.findAll", query = "SELECT s FROM StorageAccess s"),
    @NamedQuery(name = "StorageAccess.findById", query = "SELECT s FROM StorageAccess s WHERE s.id = :id"),
    @NamedQuery(name = "StorageAccess.findByAvailableSpace", query = "SELECT s FROM StorageAccess s WHERE s.availableSpace = :availableSpace")})
public class StorageAccess implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "available_space")
    private Double availableSpace;
    @OneToMany(mappedBy = "storageAccess")
    private Collection<Permission> permissionCollection;

    public StorageAccess() {
    }

    public StorageAccess(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(Double availableSpace) {
        this.availableSpace = availableSpace;
    }

    @XmlTransient
    public Collection<Permission> getPermissionCollection() {
        return permissionCollection;
    }

    public void setPermissionCollection(Collection<Permission> permissionCollection) {
        this.permissionCollection = permissionCollection;
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
        if (!(object instanceof StorageAccess)) {
            return false;
        }
        StorageAccess other = (StorageAccess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.despair.photoport.StorageAccess[ id=" + id + " ]";
    }
    
}
