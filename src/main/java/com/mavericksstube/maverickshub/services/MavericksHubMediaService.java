package com.mavericksstube.maverickshub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mavericksstube.maverickshub.dtos.requests.UpdateMediaRequest;
import com.mavericksstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.mavericksstube.maverickshub.dtos.response.MediaResponse;
import com.mavericksstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.mavericksstube.maverickshub.dtos.response.UploadMediaResponse;
import com.mavericksstube.maverickshub.exceptions.MediaNotFoundException;
import com.mavericksstube.maverickshub.exceptions.MediaUpdateFailedException;
import com.mavericksstube.maverickshub.exceptions.MediaUploadFailedException;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Media;
import com.mavericksstube.maverickshub.models.User;
import com.mavericksstube.maverickshub.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MavericksHubMediaService implements MediaService{

    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final UserService userService;


    @Override
    public UploadMediaResponse upload(UploadMediaRequest uploadMediaRequest) {
        try {
           Uploader uploader = cloudinary.uploader();

           Map<?,?> response = uploader.upload(uploadMediaRequest.getMediaFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
           log.info("cloudinary upload response: {}", response);

           String url = response.get("url").toString();

           Media media = modelMapper.map(uploadMediaRequest, Media.class);
           media.setUrl(url);
           media.setUploader(userService.getById(uploadMediaRequest.getUserId()));
           media = mediaRepository.save(media);

           return modelMapper.map(media, UploadMediaResponse.class);
       }catch (IOException exception) {
           throw new MediaUploadFailedException("media upload failed");
       } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Media getMediaBy(long id) {
        return mediaRepository.findById(id).orElseThrow(()-> new MediaNotFoundException("Media not found"));
    }

    @Override
    public UpdateMediaResponse updateMedia(Long mediaId, JsonPatch jsonPatch) {
        try {
            Media media = getMediaBy(mediaId);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode mediaNode = objectMapper.convertValue(media, JsonNode.class);
            mediaNode = jsonPatch.apply(mediaNode);
            media = objectMapper.convertValue(mediaNode, Media.class);
            media = mediaRepository.save(media);
            return modelMapper.map(media, UpdateMediaResponse.class);
        } catch (JsonPatchException exception){
            throw new MediaUpdateFailedException("media update failed");
        }
    }


    @Override
    public List<MediaResponse> getMediaFor(Long userId) throws UserNotFoundException {
        userService.getById(userId);
        List<Media> media = mediaRepository.findAllMediaFor(userId);
        return media.stream().map(m -> modelMapper.map(m, MediaResponse.class)).toList();
    }
}
