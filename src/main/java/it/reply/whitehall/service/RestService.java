package it.reply.whitehall.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * @author a.deangelis
 */
@Service
public class RestService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private HttpClient httpClient;

    @PostConstruct
    public void init() {
        this.httpClient = HttpClientBuilder.create().build();
    }


    public String sendPostRequest(URI patchRequestUri, String entityPayload, String username, String password) throws IOException {
        return sentHttpEntityRequestBase(patchRequestUri, entityPayload, new HttpPost(), username, password);
    }


    public String sentHttpEntityRequestBase(URI patchRequestUri, String entityPayload, HttpEntityEnclosingRequestBase httpRequest, String username, String password) throws IOException {
        HttpResponse httpResponse;
        httpRequest.setURI(patchRequestUri);
        String responseContent = "";
        StringEntity entity = new StringEntity(entityPayload);
        entity.setContentType("application/json");
        httpRequest.setEntity(entity);
        logger.info("SENDING REQUEST " + httpRequest.toString() + " " + entityPayload);
        httpResponse = httpClient.execute(httpRequest);
        responseContent = getResponseString(httpResponse);
        logger.info("RESPONSE " + httpResponse.getStatusLine().toString());
        logger.info(responseContent);
        return responseContent;
    }

    public void sendingGetRequest() throws Exception {

        String urlString = "http://www.omdbapi.com/?i=tt1285016&plot=short&r=json";

        URL url = new URL(urlString);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//        // By default it is GET request
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//        int responseCode = con.getResponseCode();
//        System.out.println("Sending get request : "+ url);
//        System.out.println("Response code : "+ responseCode);

        // Reading response from input Stream
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());

    }

    public String sendGetRequest(URI restGetServiceUri) throws IOException {
        String responseContent = "";
        HttpResponse httpResponse;
        HttpGet httpGet;
        httpGet = new HttpGet();
        httpGet.setURI(restGetServiceUri);
        httpGet.addHeader("User-Agent", "Mozilla/5.0");

        logger.info("SENDING GET REQUEST " + httpGet.toString());
        httpResponse = httpClient.execute(httpGet);
        responseContent = getResponseString(httpResponse);
        logger.info("RESPONSE " + httpResponse.getStatusLine().toString());
        logger.info(responseContent);
        return responseContent;
    }

    public String sendPatchRequest(URI patchRequestUri, String entityPayload, String username, String password) throws IOException {
        return sentHttpEntityRequestBase(patchRequestUri, entityPayload, new HttpPatch(), username, password);
    }


    private String getResponseString(HttpResponse httpResponse) throws IOException {
        StringBuffer result = new StringBuffer();
        if (httpResponse.getEntity() != null) {
            InputStream entityContent = httpResponse.getEntity().getContent();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(entityContent));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        return result.toString();
    }

    public URI generateUri(String host, int port, String path, List<NameValuePair> urlParameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost(host).setPort(port).setPath(path);
        if (urlParameters != null && urlParameters.size() > 0) {
            uriBuilder.addParameters(urlParameters);
        }
        return uriBuilder.build();
    }

    public URI generateUri(String host, List<NameValuePair> urlParameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost(host);
        if (urlParameters != null && urlParameters.size() > 0) {
            uriBuilder.addParameters(urlParameters);
        }
        return uriBuilder.build();
    }
}
