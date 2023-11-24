package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.io.IOException;
import java.util.Collections;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebSecurityConfig.class})
public class AdsServiceTests {

    @InjectMocks
    AdsController adsController;
    @Mock
    private AdService adService;
    @Mock
    private AdRepository adRepositoryMock;
    @InjectMocks
    private AdServiceImpl out;
    @Mock
    private Authentication authentication;
    @Mock
    private ImageService imageService;
    private final Ad testAd = new Ad();
    private final Comment testCom = new Comment();
    private final CommentDTO commentDto = new CommentDTO();
    private final CreateAdsDto createAdsDto = new CreateAdsDto();

    @BeforeEach
    public void setUp() {
        testAd.setId(1);
        testAd.setDescription("test description");
        testAd.setTitle("test title");
        testAd.setPrice(50);

        testCom.setId(2);

        commentDto.setPk(1);
        commentDto.setText("test text");

        createAdsDto.setDescription("New Description");
        createAdsDto.setTitle("New Title");
        createAdsDto.setPrice(500);
    }

    @Test
    public void getAllAdsTest() {
        List<AdsDto> adsDtoList = Collections.singletonList(new AdsDto());
        ResponseWrapperAdsDto adsDto = new ResponseWrapperAdsDto(adsDtoList);

        when(adService.getAllAdsDto()).thenReturn(adsDto);

        ResponseWrapperAdsDto responseWrapperAdsDto = adsController.getAllAds();

        verify(adService).getAllAdsDto();

        assertEquals(adsDto, responseWrapperAdsDto);
        assertNotNull(responseWrapperAdsDto);
    }

    @Test
    public void addAdTest() throws Exception {
        AdsDto adsDto = new AdsDto();
        MultipartFile image = new MockMultipartFile("test.jpg", "test.jpg",
                "image/jpeg", "test image".getBytes());

        when(adService.createAds(createAdsDto, image)).thenReturn(adsDto);

        AdsDto response = adsController.addAd(createAdsDto, image);

        verify(adService).createAds(createAdsDto, image);

        assertEquals(adsDto, response);
        assertNotNull(adsDto);
    }


    @Test
    public void getFullAdTest() {
        FullAdsDto fullAdsDto = new FullAdsDto();

        when(adService.getFullAdDto(testAd.getId())).thenReturn(fullAdsDto);

        FullAdsDto response = adsController.getAds(testAd.getId());

        verify(adService).getFullAdDto(testAd.getId());

        assertEquals(fullAdsDto, response);
        assertNotNull(fullAdsDto);
    }

    @Test
    public void removeAdTest() {
        Integer adId = 1;
        User user = new User();
        user.setEmail("user@mail.ru");
        Ad ad = new Ad();
        ad.setId(adId);
        ad.setAuthor(user);

        when(adRepositoryMock.findById(adId)).thenReturn(Optional.of(ad));
        when(authentication.getName()).thenReturn("user@mail.ru");

        out.removeAdDto(authentication, adId);
        verify(adRepositoryMock, times(1)).delete(ad);
    }

    @Test
    public void updateAdTest() {
        AdsDto adsDto = new AdsDto();
        adsDto.setTitle("New Title");
        adsDto.setPrice(500);

        when(adService.updateAdDto(authentication, testAd.getId(), createAdsDto)).thenReturn(adsDto);

        AdsDto response = adsController.updateAds(authentication, testAd.getId(), createAdsDto);

        verify(adService).updateAdDto(authentication, testAd.getId(), createAdsDto);

        assertEquals(adsDto, response);
        assertNotNull(response);
    }


    @Test
    public void getAdsMeTest() {
        List<AdsDto> adsDtoList = Collections.singletonList(new AdsDto());
        ResponseWrapperAdsDto adsDto = new ResponseWrapperAdsDto(adsDtoList);

        when(adService.getAllUserAdsDto()).thenReturn(adsDto);

        ResponseWrapperAdsDto response = adsController.getAdsForMe();

        verify(adService).getAllUserAdsDto();

        assertEquals(adsDto, response);
        assertNotNull(response);
    }

    @Test
    public void updateImageTest() throws IOException {
        MultipartFile image = new MockMultipartFile("image.jpg", new byte[]{1, 2, 3});

        doNothing().when(adService).updateImageAdDto(testAd.getId(), image);

        adsController.updateImage(testAd.getId(), image);

        verify(adService).updateImageAdDto(testAd.getId(), image);

    }

    @Test
    public void getImageTest() {
        String id = "1";
        byte[] mockImage = {1, 2, 3};

        when(imageService.getImage(id)).thenReturn(mockImage);

        byte[] response = adsController.getImage(id);

        verify(imageService).getImage(id);

        assertEquals(mockImage, response);
        assertNotNull(response);
        assertArrayEquals(mockImage, response);
    }
}