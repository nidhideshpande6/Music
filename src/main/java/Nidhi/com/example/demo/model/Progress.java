package Nidhi.com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // The user who is making progress on the track

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;  // The track that the progress is associated with

    private int listenedDuration;  // Time listened to in seconds or minutes

    private int totalDuration;  // Total track duration in seconds or minutes

    private double percentage;  // Percentage of the track that has been listened to

    private boolean completed;  // Whether the user has completed the track or not

    private Date createdAt;  // Timestamp for when the progress was created

    private Date updatedAt;  // Timestamp for when the progress was last updated

    @PrePersist
    public void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getListenedDuration() {
        return listenedDuration;
    }

    public void setListenedDuration(int listenedDuration) {
        this.listenedDuration = listenedDuration;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
