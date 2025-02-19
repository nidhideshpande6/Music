package Nidhi.com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Nidhi.com.example.demo.model.Track;

public interface TrackRepository extends JpaRepository<Track,Integer> {

}
