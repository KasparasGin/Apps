package edu.ktu.lab2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class RequestOperator extends Thread {

    public interface RequestOperatorListener{
        void success(List<ModelPost> publication);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;

    public void setListener(RequestOperatorListener listener) {this.listener = listener;}

    @Override
    public void run(){
        super.run();

        try{
            List<ModelPost> publication = request();
             if(publication!= null)
                 success(publication);
             else failed(responseCode);
        } catch(IOException e){
            failed(-1);
        } catch(JSONException e){
            failed(-2);
        }
    }

    private List<ModelPost> request() throws IOException, JSONException {

        URL obj = new URL("https://jsonplaceholder.typicode.com/posts");

        HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        InputStreamReader streamReader;

        if(responseCode == 200){
            streamReader = new InputStreamReader(con.getInputStream());
        }else {
            streamReader = new InputStreamReader(con.getErrorStream());
        }

        BufferedReader in  = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }

        in.close();

        System.out.println(response.toString());

        if(responseCode==200) {
            return parsingJsonObject(response.toString());
        } else return null;
    }

    public List<ModelPost> parsingJsonObject(String response) throws JSONException{

        JSONArray publications = new JSONArray(response);

        List<ModelPost> responses = new ArrayList<>();

        for(int i = 0; i< publications.length(); i++){
            JSONObject object = publications.getJSONObject(i);
            ModelPost post = new ModelPost();

            post.setId(object.optInt("id", 0));
            post.setUserId(object.optInt("userId", 0));

            post.setTitle(object.getString("title"));
            post.setBodyText(object.getString("body"));

            responses.add(post);
        }


        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return responses;
    }

    private void failed(int code){
        if(listener != null)
            listener.failed(code);
    }

    private void success(List<ModelPost> publication){
        if(listener != null)
            listener.success(publication);
    }
}
