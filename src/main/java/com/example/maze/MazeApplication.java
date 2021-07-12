package com.example.maze;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@SpringBootApplication
public class MazeApplication implements CommandLineRunner {

    // Todo: Consider Lombok
    // Todo: refactor this static final data into an ApiCall Object
    public static final String auth = "Authorization";
    public static final String code = "HTI Thanks You [415b]";

    public static void main(String[] args) {
        SpringApplication.run(MazeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        forget();
        register();
        JSONArray mazes = new JSONArray(getMazes());
        for(int i = 0; i<mazes.length();i++){
            JSONObject jo = (JSONObject) mazes.get(i);
            String mapName = jo.getString("name");
            Maze maze = new Maze(mapName.replace(" ", "%20"));
            maze.traverse();
        }
    }

    private String getMazes() throws Exception{
        String baseUrl = "https://maze.hightechict.nl/api/mazes/all";
        // Connect to get the data from URL
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "application/json");
        con.setRequestProperty(auth, code);
        //int status = con.getResponseCode();
        //System.out.println(status);

        // We now read the data as string
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    // API call to reset the player account and allow them to start over.
    public void forget() throws Exception {
        String baseUrl = "https://maze.hightechict.nl/api/player/forget";
        // Connect to get the data from URL
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("accept", "application/json");
        con.setRequestProperty(auth, code);
        //int status = con.getResponseCode();
        //System.out.println(status);

        // We now read the data as string
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println(content);
    }

    // API call to start the player off in the game.
    public void register() throws Exception {
        String baseUrl = "https://maze.hightechict.nl/api/player/register?name=Jason";
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("accept", "application/json");
        con.setChunkedStreamingMode(100);
        con.setDoOutput(true);
        con.setRequestProperty(auth, code);
        //int status = con.getResponseCode();
        //System.out.println(status);

        // We now read the data as string
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println(content);
    }

}
