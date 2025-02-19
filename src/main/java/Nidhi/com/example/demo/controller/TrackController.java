package Nidhi.com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Nidhi.com.example.demo.model.Track;
import Nidhi.com.example.demo.service.TrackService;

@Controller // Use @Controller to return views
@RequestMapping("tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;
    
    @GetMapping("/")
    public String homePage(Model model) {
        System.out.println("Home page method is being called"); // Add this line
        List<Track> tracks = trackService.getAllTracks();
        System.out.println("Fetched tracks: " + tracks.size());
        model.addAttribute("tracks", tracks);
        return "index"; // Ensure this returns the correct template name
    }

    
    // Show the form to add a new track
    @GetMapping("/add")
    public String showAddTrackForm() {
        return "add-track"; // Returns the add-track.html page
    }
    
    @GetMapping("/manage")
    public String manageTracks(Model model) {
        List<Track> tracks = trackService.getAllTracks();
        model.addAttribute("tracks", tracks);
        return "manage-tracks"; // Returns the manage-tracks.html page
    }


    // Add a track
    @PostMapping("/add")
    public String addTrack(@RequestParam("title") String title,
                                           @RequestParam("artist") String artist,
                                           @RequestParam("duration") int duration,
                                           @RequestParam("genre") String genre,
                                           @RequestParam("releaseDate") String releaseDate,
                                           @RequestParam("album") String album,
                                           @RequestParam("audioFile") MultipartFile audioFile, 
                                           Model model) throws IOException {
        if (audioFile.getSize() > 10 * 1024 * 1024) {  // 10MB size limit
            model.addAttribute("message", "Track too large");
            return "error"; // Return to an error page
        }

        // Convert MultipartFile to byte array
        byte[] audioData = audioFile.getBytes();

        // Create a new Track object
        Track track = new Track();
        track.setTitle(title);
        track.setArtist(artist);
        track.setDuration(duration);
        track.setGenre(genre);
        track.setReleaseDate(java.sql.Date.valueOf(releaseDate)); // Convert String to Date
        track.setAlbum(album);
        track.setAudioData(audioData);

        // Save the track
        trackService.saveTrack(track);

        model.addAttribute("message", "Track added successfully");
        return "success"; // Return to a success page
    }

    // Get all tracks
    @GetMapping("/all")
    public String getAllTracks(Model model) {
        List<Track> tracks = trackService.getAllTracks();
        model.addAttribute("tracks", tracks);
        return "track-list"; // Return to a page showing all tracks
    }

    // Get a track by ID
    @GetMapping("/{id}")
    public String getTrackById(@PathVariable int id, Model model) {
        Track track = trackService.getTrackByID(id);
        if (track != null) {
            model.addAttribute("track", track);
            return "track-detail"; // Return to a page showing track details
        } else {
            return "error"; // Return to an error page if track not found
        }
    }

    // Serve the audio file for a track by ID
    @GetMapping("/audio/{id}")
    public ResponseEntity<ByteArrayResource> getAudioFile(@PathVariable int id) {
        Track track = trackService.getTrackByID(id);
        if (track != null && track.getAudioData() != null) {
            ByteArrayResource resource = new ByteArrayResource(track.getAudioData());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"audio.mp3\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(track.getAudioData().length)
                    .body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a track by ID
    @PostMapping("/delete/{id}")
    public String deleteTrack(@PathVariable int id,Model model) {
    	System.out.println("Delete called");
        trackService.deleteTrack(id);
        model.addAttribute("tracks", trackService.getAllTracks());
        return "redirect:/tracks/all"; // Redirect to the track list after deletion
    }
}
