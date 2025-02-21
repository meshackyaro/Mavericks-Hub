package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.mavericksstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.mavericksstube.maverickshub.dtos.response.AddMediaToPlaylistResponse;
import com.mavericksstube.maverickshub.dtos.response.CreatePlaylistResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Media;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class PlayListServiceTest {

    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private MediaService mediaService;

    @Test
    public void testCreatePlaylist() throws UserNotFoundException {
        CreatePlaylistRequest createPlaylistRequest = new CreatePlaylistRequest();
        createPlaylistRequest.setName("name");
        createPlaylistRequest.setDescription("description");
        CreatePlaylistResponse response = playlistService.create(createPlaylistRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("success");
    }

    @Test
    public void addMediaToPlaylist(){
        AddMediaToPlaylistRequest addMediaRequest = new AddMediaToPlaylistRequest();
        addMediaRequest.setPlaylistId(300L);
        addMediaRequest.setMediaId(102L);
        Media media = mediaService.getMediaBy(102L);

        AddMediaToPlaylistResponse response = playlistService.addMediaToPlaylist(addMediaRequest);
        media = mediaService.getMediaBy(102L);

        assertThat(response).isNotNull();
        assertThat(response.getMedia().size()).isEqualTo(4);
    }
}
