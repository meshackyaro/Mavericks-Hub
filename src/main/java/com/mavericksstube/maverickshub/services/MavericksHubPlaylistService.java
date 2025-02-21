package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.mavericksstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.mavericksstube.maverickshub.dtos.response.AddMediaToPlaylistResponse;
import com.mavericksstube.maverickshub.dtos.response.CreatePlaylistResponse;
import com.mavericksstube.maverickshub.exceptions.PlaylistNotFoundException;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Media;
import com.mavericksstube.maverickshub.models.Playlist;
import com.mavericksstube.maverickshub.models.User;
import com.mavericksstube.maverickshub.repositories.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MavericksHubPlaylistService implements PlaylistService{

    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final MediaService mediaService;



    @Override
    public CreatePlaylistResponse create(CreatePlaylistRequest createPlaylistRequest) throws UserNotFoundException {
        Playlist newPlaylist = modelMapper.map(createPlaylistRequest, Playlist.class);
        User uploader = userService.getById(createPlaylistRequest.getUserId());
        newPlaylist.setUploader(uploader);
        Playlist savedPlaylist = playlistRepository.save(newPlaylist);
        CreatePlaylistResponse response = modelMapper.map(savedPlaylist, CreatePlaylistResponse.class);
        response.setMessage("Playlist created successfully");
        return response;
    }

    @Override
    public AddMediaToPlaylistResponse addMediaToPlaylist(AddMediaToPlaylistRequest addMediaRequest) {
//        Playlist playlist = getPlaylistBy(addMediaRequest.getPlaylistId());
//        Media media = mediaService.getMediaBy(addMediaRequest.getMediaId());
//        playlist.getMedia().add(media);
//        media.getPlaylist().add(playlist);
//        playlist = playlistRepository.save(playlist);
//
//        return modelMapper.map(playlist, AddMediaToPlaylistResponse.class);
        return null;
    }

    @Override
    public Playlist getPlaylistBy(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));
    }
}
