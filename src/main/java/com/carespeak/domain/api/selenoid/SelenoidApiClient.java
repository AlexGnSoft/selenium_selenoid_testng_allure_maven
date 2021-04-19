package com.carespeak.domain.api.selenoid;

import com.carespeak.core.logger.Logger;

import java.net.HttpURLConnection;
import java.net.URL;

public class SelenoidApiClient {

    private String baseUrl;

    public SelenoidApiClient(String url) {
        baseUrl = url;
    }

    //curl -X DELETE http://selenoid-host.example.com:4444/video/<filename>.mp4
    public void removeVideo(String videoFileName) {
        HttpURLConnection httpCon = null;
        try {
            URL url = new URL(baseUrl + "/video/" + videoFileName);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("DELETE");
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.connect();
        } catch (Throwable t) {
            Logger.error("Failed to remove video with name: " + videoFileName, t);
        } finally {
            if (httpCon != null) {
                httpCon.disconnect();
            }
        }
    }
}
