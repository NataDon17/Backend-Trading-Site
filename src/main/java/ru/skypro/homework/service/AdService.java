package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;

/**
 * Interface with CRUD methods for declaring
 */

public interface AdService {
    ResponseWrapperAdsDto getAllAdsDto();

    AdsDto createAds(CreateAdsDto adDto, MultipartFile image);

    FullAdsDto getFullAdDto(Integer id);

    void removeAdDto(Authentication authentication, Integer id);

    AdsDto updateAdDto(Authentication authentication, Integer id, CreateAdsDto createAdsDto);

    ResponseWrapperAdsDto getAllUserAdsDto();

    void updateImageAdDto(Integer id, MultipartFile image);

    boolean checkAccess(Authentication authentication, String email);
}
