package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.VideoTutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoTutorialRepository extends JpaRepository<VideoTutorial, Long> {
}