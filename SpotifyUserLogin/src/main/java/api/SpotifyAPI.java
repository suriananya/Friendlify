package api;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class SpotifyAPI {
    private static final OkHttpClient client = new OkHttpClient();

    private final String accessToken;

    public SpotifyAPI() {
        this.accessToken = requestToken();
    }

    private static String requestToken() {
        // Define the parameters for the request body
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", "53ee2a266cd542acaf19190e2ec3da41")
                .add("client_secret", "0567ae1ac8e1415ba72f748808a69377")
                .build();

        // Create the POST request to collect the API token
        Request postRequest = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();

        try {
            // Collect the response from the API requests, and return the access token
            Response response = client.newCall(postRequest).execute();
            assert response.body() != null;
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public static void main(String[] args) {
        SpotifyAPI api = new SpotifyAPI();
        System.out.println(api.getAccessToken());
    }
}
