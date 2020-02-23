package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserActivityImplTest {

    private UserActivity userActivitySpy;

    @Mock
    private UserActivityRepository userActivityRepositoryMock;

    @BeforeEach
    void setUp() {
        this.userActivitySpy = Mockito.spy(new UserActivityImpl(userActivityRepositoryMock));
    }

    @Test
    void addVideo_passingObjectToRepositoryLayer() {
        VideoDoc videoDocMock = new VideoDoc("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef("123", "shabana"));
        videoDocMock.setId("v123");
        videoDocMock.setDatePosted(LocalDateTime.now());
        when(this.userActivityRepositoryMock.addVideo(videoDocMock)).thenReturn(null);
        this.userActivitySpy.addVideo(videoDocMock);
        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.userActivityRepositoryMock, times(1)).addVideo(videoDocArgumentCaptor.capture());
        assertEquals("v123", videoDocArgumentCaptor.getValue().getId());
        assertNotNull(videoDocArgumentCaptor.getValue().getDatePosted());
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
    void addVideoLike_passingObjectToRepositoryLayer() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        when(this.userActivityRepositoryMock.addVideoLike(videoLikeUnlikeDto)).thenReturn(null);
        this.userActivitySpy.addVideoLike(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(userActivityRepositoryMock, times(1)).addVideoLike(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void deleteVideoLike_passingObjectToRepositoryLayer() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        when(this.userActivityRepositoryMock.deleteVideoLike(videoLikeUnlikeDto)).thenReturn(null);
        userActivitySpy.deleteVideoLike(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(userActivityRepositoryMock, times(1)).deleteVideoLike(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
    }

    @Test
    void iaddVideoComment_passingObjectToRepositoryLayer() {
        LocalDateTime datePosted = LocalDateTime.parse("2020-02-04 19:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        PostRef postRef = new PostRef("p123", "This is a test post title", "guide", "video", null);
        CommentRef commentRef = new CommentRef("c123", "This is a comment to test videoCommentMapper toDto()",
                postRef, datePosted);
        when(this.userActivityRepositoryMock.addVideoComment(anyString(), any())).thenReturn(null);
        userActivitySpy.addVideoComment("456", commentRef);
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<CommentRef> commentRefArgumentCaptor = ArgumentCaptor.forClass(CommentRef.class);
        verify(userActivityRepositoryMock, times(1)).addVideoComment(userIdArgumentCaptor.capture(), commentRefArgumentCaptor.capture());
        assertEquals("c123", commentRefArgumentCaptor.getValue().getId());
        assertEquals("This is a comment to test videoCommentMapper toDto()", commentRefArgumentCaptor.getValue().getComment());
        assertEquals(postRef, commentRefArgumentCaptor.getValue().getPostRef());
        assertEquals(datePosted, commentRefArgumentCaptor.getValue().getDatePosted());
    }

    @Test
    void deleteVideoComment_passingObjectToRepositoryLayer() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", null), null);
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        videoCommentDto.setId("c123");
        when(this.userActivityRepositoryMock.deleteVideoComment(any())).thenReturn(null);
        userActivitySpy.deleteVideoComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(userActivityRepositoryMock, times(1)).deleteVideoComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals("v123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }

    @Test
    void addCommentLike_passingObjectToRepositoryLayer() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("10");
        when(this.userActivityRepositoryMock.addCommentLike(any())).thenReturn(null);
        userActivitySpy.addCommentLike(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(userActivityRepositoryMock, times(1)).addCommentLike(videoCommentDtoArgumentCaptor.capture());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals("v123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("This is a test post title", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getTitle());
        assertEquals("guide", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getType());
        assertEquals("video", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getMedium());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("priyanka11", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
        assertEquals("This is a comment passed to VideoTemplateRespository", videoCommentDtoArgumentCaptor.getValue().getComment());
    }

    @Test
    void deleteCommentLike_passingObjectToRepositoryLayer() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", null), null);
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("12");
        when(this.userActivityRepositoryMock.deleteCommentLike(any())).thenReturn(null);
        userActivitySpy.deleteCommentLike(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(userActivityRepositoryMock, times(1)).deleteCommentLike(videoCommentDtoArgumentCaptor.capture());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals("v123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }


}