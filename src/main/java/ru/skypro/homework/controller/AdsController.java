package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;

/**
 * Controller class for launching endpoints for creating,
 * modifying, deleting an ad and comment
 */
@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class AdsController {
    private final AdService adService;
    private final ImageService imageService;
    private final CommentService commentService;

    @GetMapping()
    public ResponseWrapperAdsDto getAllAds() {
        return adService.getAllAdsDto();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdsDto addAd(@RequestPart CreateAdsDto properties,
                        @RequestPart MultipartFile image) {
        return adService.createAds(properties, image);
    }

    @GetMapping("/{id}")
    public FullAdsDto getAds(@PathVariable("id") Integer adId) {
        return adService.getFullAdDto(adId);
    }

    @DeleteMapping("/{id}")
    public void removeAd(Authentication authentication,
                         @PathVariable("id") Integer adId) {
        adService.removeAdDto(authentication, adId);
    }

    @PatchMapping("/{id}")
    public AdsDto updateAds(Authentication authentication,
                            @PathVariable("id") Integer adId,
                            @RequestBody CreateAdsDto adsDto) {
        return adService.updateAdDto(authentication, adId, adsDto);
    }

    @GetMapping("/me")
    public ResponseWrapperAdsDto getAdsForMe() {
        return adService.getAllUserAdsDto();
    }

    @PatchMapping("/{id}/image")
    public void updateImage(@PathVariable("id") Integer adId,
                            @RequestPart MultipartFile image) {
        adService.updateImageAdDto(adId, image);
    }

    //COMMENTS
    @GetMapping("/{id}/comments")
    public CommentsDTO getComments(@PathVariable("id") int adId) {
        return commentService.getComments(adId);
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable("id") int adId,
                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return commentService.addComment(adId, createOrUpdateCommentDTO);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(Authentication authentication,
                              @PathVariable int adId,
                              @PathVariable int commentId) {
        commentService.deleteComment(authentication, adId, commentId);
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public CommentDTO updateComment(Authentication authentication,
                                    @PathVariable int adId,
                                    @PathVariable int commentId,
                                    @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return commentService.updateComment(authentication, adId, commentId, createOrUpdateCommentDTO);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return imageService.getImage(id);
    }
}

