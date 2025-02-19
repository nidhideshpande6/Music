package Nidhi.com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Nidhi.com.example.demo.model.Track;
import Nidhi.com.example.demo.service.TrackService;

@Controller
public class HomeController {
	
	@Autowired
	private TrackService trackService;
	
	@GetMapping("/")
    public String homePage(Model model) {
        System.out.println("Home page method is being called"); // Add this line
        List<Track> tracks = trackService.getAllTracks();
        System.out.println("Fetched tracks: " + tracks.size());
        model.addAttribute("tracks", tracks);
        model.addAttribute("appName", "Music Track Management");
        return "index"; // Ensure this returns the correct template name
    }
	
	/*
	 * @GetMapping("/") public String showHomePage(Model model) { // Pass any
	 * required attributes to the view model.addAttribute("appName",
	 * "Music Track Management"); return "index"; // This maps to the index.html
	 * template }
	 */
    
    @GetMapping("/admin")
    public String adminDashboard() {
        // Return the name of the view for the admin dashboard (admin.html)
        return "admin"; 
    }

    @GetMapping("/track-list")
    public String userTrackList() {
        // Return the name of the view for the user track list (track-list.html)
        return "track-list"; 
    }
    
    @GetMapping("/index")
    public String login() {
        // Return the name of the view for the user track list (track-list.html)
        return "index"; 
    }

}

