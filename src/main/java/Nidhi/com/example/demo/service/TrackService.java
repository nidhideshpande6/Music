package Nidhi.com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import Nidhi.com.example.demo.model.Track;
import Nidhi.com.example.demo.repository.TrackRepository;

@Service
public class TrackService {
	
	@Autowired
	TrackRepository trackRepository;
	
	public Track saveTrack(Track track) {
		return trackRepository.save(track);
	}
	
	public Track getTrackByID(@ModelAttribute int id) {
		return trackRepository.findById(id).orElse(null);
	}
	
	public List<Track> getAllTracks(){
		return (trackRepository.findAll());
	}
	
	public void deleteTrack(@ModelAttribute int id) {
		trackRepository.deleteById(id);
	}
}
