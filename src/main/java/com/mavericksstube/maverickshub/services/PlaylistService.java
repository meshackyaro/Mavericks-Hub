package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.mavericksstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.mavericksstube.maverickshub.dtos.response.AddMediaToPlaylistResponse;
import com.mavericksstube.maverickshub.dtos.response.CreatePlaylistResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Playlist;


public interface PlaylistService {
    CreatePlaylistResponse create(CreatePlaylistRequest createPlaylistRequest) throws UserNotFoundException;

    AddMediaToPlaylistResponse addMediaToPlaylist(AddMediaToPlaylistRequest addMediaRequest);

    Playlist getPlaylistBy(Long id);
}
