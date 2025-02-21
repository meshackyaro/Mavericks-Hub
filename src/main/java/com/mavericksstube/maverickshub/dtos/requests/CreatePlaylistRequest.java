package com.mavericksstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePlaylistRequest {
    private String name;
    private String description;
    private Long userId;
}
