package com.despair.photoport.client.flickr.tasks;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.despair.photoport.client.flickr.FlickrHelper;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.googlecode.flickrjandroid.uploader.Uploader;

import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by root on 6/10/14.
 */
public class UploadPhotoTask extends AsyncTask<Void, Void, Void> {
    public static Bitmap Bitmap;
    public static String Name;

    @Override
    protected Void doInBackground(Void... params) {
        String CALLBACK_SCHEME = "flickrj-android-sample-oauth";
        Uri OAUTH_CALLBACK_URI = Uri.parse(CALLBACK_SCHEME + "://oauth");

        Flickr f = FlickrHelper.getInstance().getFlickr();
        OAuthToken oauthToken = null;

        try {
            oauthToken = f.getOAuthInterface().getRequestToken(OAUTH_CALLBACK_URI.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FlickrException e) {
            e.printStackTrace();
        }

        f = FlickrHelper.getInstance().getFlickrAuthed(oauthToken.getOauthToken(), oauthToken.getOauthTokenSecret());
        Uploader uploader = f.getUploader();

        UploadMetaData metadata = new UploadMetaData();
        metadata.setAsync(false);
        metadata.setHidden(true);
        metadata.setPublicFlag(false);

        try {
            String id = uploader.upload(Name, bitmapToByteArray(Bitmap), metadata);
            Log.w("API", String.format("Photo id = %s", id));
        } catch (FlickrException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }
}