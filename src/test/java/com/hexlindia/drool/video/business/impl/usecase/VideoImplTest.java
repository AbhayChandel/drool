package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.video.data.doc.ProductRef;
import com.hexlindia.drool.video.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.ProductRefDto;
import com.hexlindia.drool.video.dto.UserRefDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class VideoImplTest {

    private VideoImpl videoImplSpy;

    @Mock
    private VideoDocDtoMapper videoDocDtoMapperMock;

    @Mock
    private VideoTemplateRepository videoTemplateRepository;

    @BeforeEach
    void setUp() {
        this.videoImplSpy = Mockito.spy(new VideoImpl(videoDocDtoMapperMock, videoTemplateRepository));
    }

    @Test
    void insert_PassingObjectToRepositoryLayer() {
        VideoDoc videoDocMock = new VideoDoc("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef("123", "shabana"));
        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(videoDocMock);
        when(this.videoTemplateRepository.insert((VideoDoc) any())).thenReturn(videoDocMock);
        this.videoImplSpy.insert(null);
        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.videoTemplateRepository, times(1)).insert(videoDocArgumentCaptor.capture());
        assertEquals("review", videoDocArgumentCaptor.getValue().getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDocArgumentCaptor.getValue().getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDocArgumentCaptor.getValue().getDescription());
        assertEquals("vQ765gh", videoDocArgumentCaptor.getValue().getSourceId());
        assertEquals(2, videoDocArgumentCaptor.getValue().getProductRefList().size());
        assertEquals("abc", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getId());
        assertEquals("Loreal Kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getName());
        assertEquals("kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getType());
        assertEquals("123", videoDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", videoDocArgumentCaptor.getValue().getUserRef().getUsername());
    }

    @Test
    void insert_ObjectReturnedFromRepositoryLayerIsReceived() {
        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(new VideoDoc());
        when(this.videoTemplateRepository.insert((VideoDoc) any())).thenReturn(new VideoDoc());
        VideoDto videoDtoMock = new VideoDto("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        videoDtoMock.setId("456");
        when(this.videoDocDtoMapperMock.toDto(any())).thenReturn(videoDtoMock);
        VideoDto videoDto = this.videoImplSpy.insert(null);
        assertEquals("456", videoDto.getId());
        assertEquals("L'oreal Collosal Kajal Review", videoDto.getTitle());
    }

    @Test
    void findById_testPassingEntityToRepository() {
        when(this.videoTemplateRepository.findByIdAndActiveTrue("abc")).thenReturn(new VideoDoc());
        videoImplSpy.findById("abc");
        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(videoTemplateRepository, times(1)).findByIdAndActiveTrue(idArgumentCaptor.capture());
        assertEquals("abc", idArgumentCaptor.getValue());
    }

    @Test
    void findById_testFindUnavailableVideo() {
        when(this.videoTemplateRepository.findByIdAndActiveTrue("abc")).thenReturn(null);
        Assertions.assertThrows(VideoNotFoundException.class, () -> videoImplSpy.findById("abc"));
    }

    @Test
    void incrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        when(this.videoTemplateRepository.incrementLikes(videoLikeUnlikeDto)).thenReturn(true);
        videoImplSpy.incrementLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoTemplateRepository, times(1)).incrementLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void decrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        when(this.videoTemplateRepository.decrementLikes(videoLikeUnlikeDto)).thenReturn(true);
        videoImplSpy.decrementLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoTemplateRepository, times(1)).decrementLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals(null, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

}