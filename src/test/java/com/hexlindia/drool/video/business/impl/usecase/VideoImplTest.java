package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.mapper.VideoCommentMapper;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class VideoImplTest {

    private VideoImpl videoImplSpy;

    @Mock
    private VideoDocDtoMapper videoDocDtoMapperMock;

    @Mock
    private VideoTemplateRepository videoTemplateRepositoryMock;

    @Mock
    private VideoCommentMapper videoCommentMapperMock;

    @Mock
    private PostRefMapper postRefMapperMock;

    @BeforeEach
    void setUp() {
        this.videoImplSpy = Mockito.spy(new VideoImpl(videoDocDtoMapperMock, videoTemplateRepositoryMock, videoCommentMapperMock, postRefMapperMock));
    }

    @Test
    void insert_PassingObjectToRepositoryLayer() {
        VideoDoc videoDocMock = new VideoDoc("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef("123", "shabana"));
        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(videoDocMock);
        when(this.videoTemplateRepositoryMock.insert((VideoDoc) any())).thenReturn(videoDocMock);
        this.videoImplSpy.insert(null);
        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.videoTemplateRepositoryMock, times(1)).insert(videoDocArgumentCaptor.capture());
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
        when(this.videoTemplateRepositoryMock.insert((VideoDoc) any())).thenReturn(new VideoDoc());
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
        when(this.videoTemplateRepositoryMock.findByIdAndActiveTrue("abc")).thenReturn(new VideoDoc());
        videoImplSpy.findById("abc");
        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(videoTemplateRepositoryMock, times(1)).findByIdAndActiveTrue(idArgumentCaptor.capture());
        assertEquals("abc", idArgumentCaptor.getValue());
    }

    @Test
    void findById_testFindUnavailableVideo() {
        when(this.videoTemplateRepositoryMock.findByIdAndActiveTrue("abc")).thenReturn(null);
        Assertions.assertThrows(VideoNotFoundException.class, () -> videoImplSpy.findById("abc"));
    }

    @Test
    void incrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        when(this.videoTemplateRepositoryMock.incrementLikes(videoLikeUnlikeDto)).thenReturn("123");
        videoImplSpy.incrementLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoTemplateRepositoryMock, times(1)).incrementLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void decrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        when(this.videoTemplateRepositoryMock.decrementLikes(videoLikeUnlikeDto)).thenReturn("123");
        videoImplSpy.decrementLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoTemplateRepositoryMock, times(1)).decrementLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals(null, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void insertComment_testPassingArgumentsToVideoRespository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        when(this.videoTemplateRepositoryMock.insertComment(any(), any())).thenReturn(null);
        when(this.videoCommentMapperMock.toDoc(videoCommentDto)).thenReturn(new VideoComment(new UserRef("456", "priyanka11"), null, "This is a comment to test videoCommentMapper toDto()"));
        when(this.postRefMapperMock.toDoc(postRefDtoMocked)).thenReturn(new PostRef("v123", "This is a test post title", "guide", "video", null));
        videoImplSpy.insertComment(videoCommentDto);
        ArgumentCaptor<VideoComment> videoCommentArgumentCaptor = ArgumentCaptor.forClass(VideoComment.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(videoTemplateRepositoryMock, times(1)).insertComment(postRefArgumentCaptor.capture(), videoCommentArgumentCaptor.capture());
        assertEquals("v123", postRefArgumentCaptor.getValue().getId());
        assertNotNull(videoCommentArgumentCaptor.getValue().getId());
        assertEquals("456", videoCommentArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("priyanka11", videoCommentArgumentCaptor.getValue().getUserRef().getUsername());
        assertEquals("This is a comment to test videoCommentMapper toDto()", videoCommentArgumentCaptor.getValue().getComment());
    }

    @Test
    void insertComment_testPassingEntityToUserActivityRepository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        when(this.videoTemplateRepositoryMock.insertComment(any(), any())).thenReturn(null);
        when(this.videoCommentMapperMock.toDoc(videoCommentDto)).thenReturn(new VideoComment(new UserRef("456", "priyanka11"), null, "This is a comment to test videoCommentMapper toDto()"));
        when(this.postRefMapperMock.toDoc(postRefDtoMocked)).thenReturn(new PostRef("v123", "This is a test post title", "guide", "video", null));
        videoImplSpy.insertComment(videoCommentDto);
        ArgumentCaptor<VideoComment> videoCommentArgumentCaptor = ArgumentCaptor.forClass(VideoComment.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(videoTemplateRepositoryMock, times(1)).insertComment(postRefArgumentCaptor.capture(), videoCommentArgumentCaptor.capture());
        assertEquals("v123", postRefArgumentCaptor.getValue().getId());
        assertNotNull(videoCommentArgumentCaptor.getValue().getId());
        assertEquals("456", videoCommentArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("priyanka11", videoCommentArgumentCaptor.getValue().getUserRef().getUsername());
        assertEquals("This is a comment to test videoCommentMapper toDto()", videoCommentArgumentCaptor.getValue().getComment());
    }

    @Test
    void deleteComment_testPassingArgumentsToVideoRespository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", null), null);
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        videoCommentDto.setId("c123");
        when(this.videoTemplateRepositoryMock.deleteComment(any())).thenReturn(true);
        videoImplSpy.deleteComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoTemplateRepositoryMock, times(1)).deleteComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals("v123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }

}