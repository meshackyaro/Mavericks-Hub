package com.mavericksstube.maverickshub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.mavericksstube.maverickshub.dtos.requests.UpdateMediaRequest;
import com.mavericksstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.mavericksstube.maverickshub.dtos.response.MediaResponse;
import com.mavericksstube.maverickshub.dtos.response.UploadMediaResponse;
import com.mavericksstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Media;

import java.util.List;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest uploadMediaRequest);

    Media getMediaBy(long id);

    UpdateMediaResponse updateMedia(Long mediaId, JsonPatch updateMediaRequest);

    List<MediaResponse> getMediaFor(Long userId) throws UserNotFoundException;
}
