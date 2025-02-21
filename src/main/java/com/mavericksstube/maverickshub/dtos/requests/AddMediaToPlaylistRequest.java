package com.mavericksstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddMediaToPlaylistRequest {
    private Long playlistId;
    private Long mediaId;
}
