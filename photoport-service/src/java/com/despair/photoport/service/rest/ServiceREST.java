package com.despair.photoport.service.rest;

/*
 *  * To change this license header, choose License Headers in Project Properties.
 *   * To change this template file, choose Tools | Templates
 *    * and open the template in the editor.
 *     */
import com.despair.photoport.service.Alias;
import com.despair.photoport.service.Device;
import com.despair.photoport.service.Provider;
import com.despair.photoport.service.Reciver;
import com.despair.photoport.service.LinkedDevices;
import com.despair.photoport.service.FlickrUser;
import com.despair.photoport.service.FlickrUserDevice;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *  *
 *   * @author root
 *
 */
enum AccessMode {

    Provider,
    Reciver
}

@Stateless
@Path("com.despair.photoport.api")
public class ServiceREST {

    @PersistenceContext(unitName = "photoport-servicePU")
    private EntityManager em;

    public ServiceREST() {
    }

    @GET
    @Path("register/hash={hash}&email={email}&mode={mode}")
    @Consumes({"application/xml", "application/json"})
    public Device register(@PathParam("hash") String hash, @PathParam("email") String email, @PathParam("mode") String mode) {
        Device device = null;

        if (mode.equals("Provider")) {
            device = new Device();
            device.setHash(hash);
            device.setEmail(email);
            getEntityManager().persist(device);

            Provider provider = new Provider();
            provider.setProviderId(device);
            getEntityManager().persist(provider);

        } else if (mode.equals("Reciver")) {
            device = new Device();
            device.setHash(hash);
            device.setEmail(email);
            getEntityManager().persist(device);

            Reciver reciver = new Reciver();
            reciver.setReciverId(device);
            getEntityManager().persist(reciver);
        }

        return device;
    }

    @GET
    @Path("generate_access_code/hash={hash}")
    @Consumes({"application/xml", "application/json"})
    public Alias generateAccessCode(@PathParam("hash") String hash) {
        Query q = getEntityManager().createQuery("SELECT d FROM Device d WHERE d.hash = :hash");
        q.setParameter("hash", hash);
        List results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return null;
        }

        Device device = (Device) results.get(0);

        q = getEntityManager().createQuery("SELECT p FROM Provider p WHERE p.providerId = :id");
        q.setParameter("id", device);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return null;
        }

        Provider provider = (Provider) q.getResultList().get(0);
        Alias alias = new Alias();
        long time = Calendar.getInstance().getTimeInMillis();
        alias.setTimestamp(new BigInteger(Long.toString(time)));
        alias.setGeneratedCode(generateCode(5));
        alias.setProviderId(provider);

        getEntityManager().persist(alias);
        return alias;
    }

    @POST
    @Path("uncouple/hash={hash}")
    public void uncouple(@PathParam("hash") String hash) {
        Query q = getEntityManager().createQuery("SELECT d FROM Device d WHERE d.hash = :hash");
        q.setParameter("hash", hash);
        List results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return;
        }

        Device device = (Device) results.get(0);

        q = getEntityManager().createQuery("SELECT p FROM Provider p WHERE p.providerId = :id");
        q.setParameter("id", device);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            q = getEntityManager().createQuery("SELECT r FROM Reciver r WHERE r.reciverId = :id");
            q.setParameter("id", device);
            results = q.getResultList();

            if (results == null || results.isEmpty()) {
                return;
            }

            Reciver reciver = (Reciver) q.getResultList().get(0);

            q = getEntityManager().createQuery("DELETE FROM LinkedDevices l WHERE l.reciverId = :reciver");
            q.setParameter("reciver", reciver);
            q.executeUpdate();

            return;
        }

        Provider provider = (Provider) q.getResultList().get(0);

        q = getEntityManager().createQuery("DELETE FROM LinkedDevices l WHERE l.providerId = :provider");
        q.setParameter("provider", provider);
        q.executeUpdate();
    }

    @GET
    @Path("couple/generated_code={code}&reciver={hash}")
    @Consumes({"application/xml", "application/json"})
    public String couple(@PathParam("code") String code, @PathParam("hash") String hash) {
        Query q = getEntityManager().createQuery("SELECT a FROM Alias a WHERE a.generatedCode = :code");
        q.setParameter("code", code);
        List results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return "Code not found";
        }

        Alias alias = (Alias) results.get(0);

        q = getEntityManager().createQuery("SELECT d FROM Device d WHERE d.hash = :hash");
        q.setParameter("hash", hash);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return String.format("Device with hash '%s' not found", hash);
        }

        Device device = (Device) results.get(0);

        q = getEntityManager().createQuery("SELECT r FROM Reciver r WHERE r.reciverId = :reciver");
        q.setParameter("reciver", device);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return String.format("Reciver with hash '%s' not found", hash);
        }

        Reciver reciver = (Reciver) results.get(0);

        LinkedDevices link = new LinkedDevices();
        link.setProviderId(alias.getProviderId());
        link.setReciverId(reciver);

        long time = Calendar.getInstance().getTimeInMillis();
        long diff = time - Long.parseLong(alias.getTimestamp().toString());

        if (diff < 5 * 60 * 1000) {
            getEntityManager().persist(link);
            return "OK";
        }

        return "Token are expired";
    }

    String abc = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected String generateCode(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(abc.charAt((int) (Math.random() * abc.length())));
        }

        return builder.toString();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("update_flickr_user/hash={hash}&oauth_token={oauth_token}&token_secret={token_secret}&username={username}&user_id={user_id}")
    @Consumes({"application/xml", "application/json"})
    public FlickrUser UpdateOrAddFlickrUser(
            @PathParam("hash") String hash,
            @PathParam("oauth_token") String oauth_token,
            @PathParam("token_secret") String token_secret,
            @PathParam("username") String username,
            @PathParam("user_id") String user_id) {

        Query q = getEntityManager().createQuery("SELECT d FROM Device d WHERE d.hash = :hash");
        q.setParameter("hash", hash);
        List results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return null;
        }

        Device device = (Device) results.get(0);

        q = getEntityManager().createQuery("SELECT f FROM FlickrUserDevice f WHERE f.deviceId = :id");
        q.setParameter("id", device);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            FlickrUser flickr_user = new FlickrUser();
            flickr_user.setOauthToken(oauth_token);
            flickr_user.setTokenSecret(token_secret);
            flickr_user.setUsername(username);
            flickr_user.setUserId(user_id);

            getEntityManager().persist(flickr_user);

            FlickrUserDevice flickr_user_device = new FlickrUserDevice();
            flickr_user_device.setDeviceId(device);
            flickr_user_device.setFlickrUserId(flickr_user);

            getEntityManager().persist(flickr_user_device);
            return flickr_user;
        } else {
            FlickrUserDevice flickr_user_device = (FlickrUserDevice) q.getResultList().get(0);

            q = getEntityManager().createQuery("SELECT f FROM FlickrUser f WHERE f.id = :id");
            q.setParameter("id", flickr_user_device.getId());
            results = q.getResultList();

            if (results == null || results.isEmpty()) {
                return null;
            }

            FlickrUser flickr_user = (FlickrUser) results.get(0);
            flickr_user.setOauthToken(oauth_token);
            flickr_user.setTokenSecret(token_secret);
            flickr_user.setUsername(username);
            flickr_user.setUserId(user_id);

            getEntityManager().persist(flickr_user);
            return flickr_user;
        }
    }

    @GET
    @Path("provider/reciver={hash}")
    @Consumes({"application/xml", "application/json"})
    public String getProvider(@PathParam("hash") String hash) {
        Query q = getEntityManager().createQuery("SELECT d FROM Device d WHERE d.hash = :hash");
        q.setParameter("hash", hash);
        List results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return String.format("Device with hash '%s' not found", hash);
        }

        Device device = (Device) results.get(0);

        q = getEntityManager().createQuery("SELECT r FROM Reciver r WHERE r.reciverId = :id");
        q.setParameter("id", device);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return String.format("Reciver with hash '%s' not found", hash);
        }

        Reciver reciver = (Reciver) q.getResultList().get(0);

        q = getEntityManager().createQuery("SELECT l FROM LinkedDevices l WHERE l.reciverId = :id");
        q.setParameter("id", reciver);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            return String.format("Link with hash '%s' not found", hash);
        } else {
            LinkedDevices link = (LinkedDevices) results.get(0);
            return link.getProviderId().getProviderId().getHash();
        }
    }

    @GET
    @Path("flickr/reciver={hash}")
    @Consumes({"application/xml", "application/json"})
    public FlickrUser getFlickr(@PathParam("hash") String hash) {
        Query q = getEntityManager().createQuery("SELECT d FROM Device d WHERE d.hash = :hash");
        q.setParameter("hash", hash);
        List results = q.getResultList();

        if (results == null || results.isEmpty()) {
            //return String.format("Device with hash '%s' not found", hash);
            return null;
        }

        Device device = (Device) results.get(0);

        q = getEntityManager().createQuery("SELECT r FROM Reciver r WHERE r.reciverId = :id");
        q.setParameter("id", device);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            //return String.format("Reciver with hash '%s' not found", hash);
            return null;
        }

        Reciver reciver = (Reciver) q.getResultList().get(0);

        q = getEntityManager().createQuery("SELECT l FROM LinkedDevices l WHERE l.reciverId = :id");
        q.setParameter("id", reciver);
        results = q.getResultList();

        if (results == null || results.isEmpty()) {
            //return String.format("Link with hash '%s' not found", hash);
            return null;
        } else {
            LinkedDevices link = (LinkedDevices) results.get(0);
            Device flickr_owner = link.getProviderId().getProviderId();

            q = getEntityManager().createQuery("SELECT f FROM FlickrUserDevice f WHERE f.deviceId = :id");
            q.setParameter("id", flickr_owner);
            results = q.getResultList();

            if (results == null || results.isEmpty()) {
                //return "Flickr user not found";
                return null;
            } else {
                FlickrUserDevice flickr = (FlickrUserDevice) results.get(0);

                q = getEntityManager().createQuery("SELECT f FROM FlickrUserDevice f WHERE f.deviceId = :id");
                q.setParameter("id", flickr_owner);
                results = q.getResultList();

                if (results == null || results.isEmpty()) {
                    //return "Flickr user not found";
                    return null;
                } else {
                    FlickrUserDevice flickr_user = (FlickrUserDevice) results.get(0);
                    return flickr_user.getFlickrUserId();
                }
            }
        }
    }
}
