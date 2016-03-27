package com.example.rishab.complaintsystem;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

/**
 * Created by Rishab on 26-03-2016.
 */
public class SingletonNetworkClass {
    private static SingletonNetworkClass single_netwrk;
    private RequestQueue requestQueue;
    private static Context mcontext;

    //setting the context
    public SingletonNetworkClass(Context context) {
        mcontext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonNetworkClass getInstance(Context context) {
        if (single_netwrk == null) {

            single_netwrk = new SingletonNetworkClass(context);
        }
        return single_netwrk;
    }

    //get the request queue
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            DefaultHttpClient mDefaultHttpClient = new DefaultHttpClient();

            final ClientConnectionManager mClientConnectionManager = mDefaultHttpClient.getConnectionManager();
            final HttpParams mHttpParams = mDefaultHttpClient.getParams();
            final ThreadSafeClientConnManager mThreadSafeClientConnManager = new ThreadSafeClientConnManager( mHttpParams, mClientConnectionManager.getSchemeRegistry() );

            mDefaultHttpClient = new DefaultHttpClient( mThreadSafeClientConnManager, mHttpParams );

            final HttpStack httpStack = new HttpClientStack( mDefaultHttpClient );
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext(), httpStack);
        }
        return requestQueue;
    }

    //add a generic request to the the queue
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
