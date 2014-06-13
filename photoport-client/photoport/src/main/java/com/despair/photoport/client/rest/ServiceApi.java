package com.despair.photoport.client.rest;

import com.despair.photoport.client.rest.entities.Alias;
import com.despair.photoport.client.rest.entities.Device;
import com.despair.photoport.client.rest.entities.FlickrUser;
import com.despair.photoport.client.rest.entities.LinkedDevices;
import com.despair.photoport.client.rest.entities.Provider;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * Created by root on 6/12/14.
 */

@Rest(rootUrl = "http://192.168.12.1:8080/photoport-service/webresources/com.despair.photoport.api", converters = {MappingJacksonHttpMessageConverter.class})
public interface ServiceApi {

    @Get("/register/hash={hash}&email={email}&mode={mode}")
    @Accept(MediaType.APPLICATION_JSON)
    public Device register(String hash, String email, String mode);

    @Get("/generate_access_code/hash={hash}")
    @Accept(MediaType.APPLICATION_JSON)
    public Alias generateAccessCode(String hash);

    @Post("/uncouple/hash={hash}")
    @Accept(MediaType.APPLICATION_JSON)
    public void uncouple(String hash);

    @Get("/couple/generated_code={code}&reciver={hash}")
    @Accept(MediaType.APPLICATION_JSON)
    public LinkedDevices couple(String code, String hash);

    @Get("/update_flickr_user/hash={hash}&oauth_token={oauth_token}&token_secret={token_secret}&username={username}&user_id={user_id}")
    @Accept(MediaType.APPLICATION_JSON)
    public FlickrUser updateOrAddFlickrUser(String hash, String oauth_token, String token_secret, String username, String user_id);

    @Get("/provider/hash={hash}")
    @Accept(MediaType.APPLICATION_JSON)
    public Provider getProvider(String hash);

    @Get("/flickr/hash={hash}")
    @Accept(MediaType.APPLICATION_JSON)
    public FlickrUser getFlickr(String hash);
}
