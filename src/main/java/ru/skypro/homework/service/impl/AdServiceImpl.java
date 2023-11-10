package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exeption.AdNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.AdMapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A class with crud methods for the declaration and its image
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdMapper adMapper;
    private final UserService userService;
    private final AdRepository adRepository;
    private final ImageService imageService;

    /**
     * Method for getting a list of all ads
     */
    @Override
    public ResponseWrapperAdsDto getAllAdsDto() {
        Collection<AdsDto> adsAll = adMapper.mapAdListToAdDtoList(adRepository.findAll());
        return new ResponseWrapperAdsDto(adsAll);
    }

    /**
     * Method for creating an ad
     */
    @Override
    public AdsDto createAds(CreateAdsDto adDto, MultipartFile image) {
        Ad newAd = adMapper.mapCreatedAdsDtoToAd(adDto);
        newAd.setAuthor(userService.findAuthUser().orElseThrow());
        Image newImage = imageService.createImage(image);
        newAd.setImage(newImage);
        adRepository.save(newAd);
        return adMapper.mapAdToAdDto(newAd);
    }

    /**
     * Method for getting the ad
     */
    @Override
    public FullAdsDto getFullAdDto(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return adMapper.mapAdToFullAdsDTo(ad);
    }

    /**
     * Method for deleting an ad by ID
     */
    @Override
    public boolean removeAdDto(Integer id) {
        if (checkAccess(id)) {
            adRepository.deleteById(id);
            return true;
        }
        throw new AdNotFoundException();
    }

    /**
     * Method for changing the ad by ID
     */
    @Override
    public AdsDto updateAdDto(Integer id, CreateAdsDto createAdsDto) {
        Ad ad = adRepository.findById(id).orElseThrow();
        if (checkAccess(id)) {
            ad.setTitle(createAdsDto.getTitle());
            ad.setPrice(createAdsDto.getPrice());
            ad.setDescription(createAdsDto.getDescription());
            adRepository.save(ad);
            return adMapper.mapAdToAdDto(ad);
        }
        throw new AdNotFoundException();
    }

    /**
     * Method for getting all the user's ads
     */
    @Override
    public ResponseWrapperAdsDto getAllUserAdsDto() {
        User user = userService.findAuthUser().orElseThrow();
        Collection<Ad> allAds = adRepository.findAll();
        Collection<Ad> userAds = allAds.stream()
                .filter(x -> x.getAuthor().equals(user))
                .collect(Collectors.toList());
        return new ResponseWrapperAdsDto(adMapper.mapAdListToAdDtoList(userAds));
    }

    /**
     * Method for changing the ad image
     */
    @Override
    public void updateImageAdDto(Integer id, MultipartFile image) {
        Ad ad = adRepository.findById(id).orElseThrow();
        Image updImage = imageService.updateImage(image, ad.getImage());
        ad.setImage(updImage);
        adRepository.save(ad);
    }

    /**
     * Method for checking access by the administrator
     */
    @Override
    public boolean checkAccess(Integer id) {
        Role role = Role.ADMIN;
        Ad ad = adRepository.findById(id).orElseThrow();
        User user = userService.findAuthUser().orElseThrow();
        String curName = user.getUsername();
        return ad.getAuthor().getUsername().equals(curName)
                || user.getAuthorities().contains(role);
    }
}
