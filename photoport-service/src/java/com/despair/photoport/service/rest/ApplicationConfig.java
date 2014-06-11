/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.despair.photoport.service.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author root
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.despair.photoport.service.rest.AliasFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.DeviceFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.FlickrUserDeviceFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.FlickrUserFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.LinkedDevicesFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.PermissionFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.PermissionTypeFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.ProviderFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.ReciverFacadeREST.class);
        resources.add(com.despair.photoport.service.rest.ServiceREST.class);
        resources.add(com.despair.photoport.service.rest.StorageAccessFacadeREST.class);
    }
    
}
