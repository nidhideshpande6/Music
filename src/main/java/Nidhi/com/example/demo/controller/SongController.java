package Nidhi.com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class SongController {

    private static final String CLIENT_ID = "797c07b4"; // Replace with your Jamendo Client ID
    private static final String API_URL = "https://api.jamendo.com/v3.0/tracks/?client_id=" + CLIENT_ID;

    @GetMapping("/song-list")
    public String fetchSongs(@RequestParam(defaultValue = "1") int page, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        
        int limit = 5;  // Number of songs per page
        int offset = (page - 1) * limit;  // Calculate offset
        
        String apiUrl = API_URL + "&limit=" + limit + "&offset=" + offset;  // Corrected URL
        
        String response = restTemplate.getForObject(apiUrl, String.class);
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");

        List<Map<String, String>> songs = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject song = results.getJSONObject(i);
            Map<String, String> songDetails = new HashMap<>();
            songDetails.put("id", song.getString("id"));
            songDetails.put("name", song.getString("name"));
            songDetails.put("artist", song.getString("artist_name"));
            songDetails.put("album", song.optString("album_name", "Unknown")); // Handle missing album
            songDetails.put("audio", song.optString("audio", ""));  // Handle missing audio
            songDetails.put("release", song.optString("releasedate", "N/A")); // Handle missing release date

            songs.add(songDetails);
        }

        model.addAttribute("songs", songs);
        model.addAttribute("currentPage", page);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("prevPage", page > 1 ? page - 1 : 1);

        return "song-list";
    }
    
    
    @GetMapping("/song/{id}")
    public String fetchSongById(@PathVariable String id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = API_URL + "&id=" + id;  // Fetching a specific song by ID

        String response = restTemplate.getForObject(apiUrl, String.class);
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");

        if (results.length() > 0) {
            JSONObject song = results.getJSONObject(0);
            Map<String, String> songDetails = new HashMap<>();
            songDetails.put("id", song.getString("id"));
            songDetails.put("name", song.getString("name"));
            songDetails.put("artist", song.getString("artist_name"));
            songDetails.put("album", song.optString("album_name", "Unknown"));
            songDetails.put("audio", song.optString("audio", ""));
            songDetails.put("release", song.optString("releasedate", "N/A"));

            model.addAttribute("song", songDetails);
        } else {
            model.addAttribute("error", "Song not found.");
        }

        return "song-details"; // This should map to a Thymeleaf template for displaying the song details.
    }

}
