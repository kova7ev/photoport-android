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
@Table(name = "reciver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reciver.findAll", query = "SELECT r FROM Reciver r"),
    @NamedQuery(name = "Reciver.findById", query = "SELECT r FROM Reciver r WHERE r.id = :id")})
public class Reciver implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "reciverId")
    private Collection<LinkedDevices> linkedDevicesCollection;
    @JoinColumn(name = "reciver_id", referencedColumnName = "id")
    @ManyToOne
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

    @XmlTransient
    public Collection<LinkedDevices> getLinkedDevicesCollection() {
        return linkedDevicesCollection;
    }

    public void setLinkedDevicesCollection(Collection<LinkedDevices> linkedDevicesCollection) {
        this.linkedDevicesCollection = linkedDevicesCollection;
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
