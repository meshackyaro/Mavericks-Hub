package com.mavericksstube.maverickshub.repositories;

import com.mavericksstube.maverickshub.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
