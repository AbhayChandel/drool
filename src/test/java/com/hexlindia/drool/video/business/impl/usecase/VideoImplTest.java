package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.data.doc.CommentRef;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.mapper.VideoCommentMapper;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.dto.mapper.VideoThumbnailDataMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

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
    private VideoRepository videoRepositoryMock;

    @Mock
    private UserActivity userActivityMock;

    @Mock
    private VideoCommentMapper videoCommentMapperMock;

    @Mock
    private PostRefMapper postRefMapperMock;

    @Mock
    private VideoThumbnailDataMapper videoThumbnailDataMapperMock;

    @Mock
    private ActivityFeed activityFeedMock;

    @BeforeEach
    void setUp() {
        this.videoImplSpy = Mockito.spy(new VideoImpl(videoDocDtoMapperMock, videoRepositoryMock, videoCommentMapperMock, postRefMapperMock, userActivityMock, videoThumbnailDataMapperMock, activityFeedMock));
    }

    @Test
    void save_PassingObjectToRepositoryLayer() {
        ObjectId userId = new ObjectId();
        VideoDoc videoDocMock = new VideoDoc("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef(userId, "shabana"));
        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(videoDocMock);
        when(this.videoRepositoryMock.save(any())).thenReturn(videoDocMock);
        this.videoImplSpy.save(null);
        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.videoRepositoryMock, times(1)).save(videoDocArgumentCaptor.capture());
        assertEquals("review", videoDocArgumentCaptor.getValue().getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDocArgumentCaptor.getValue().getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDocArgumentCaptor.getValue().getDescription());
        assertEquals("vQ765gh", videoDocArgumentCaptor.getValue().getSourceId());
        assertEquals(2, videoDocArgumentCaptor.getValue().getProductRefList().size());
        assertEquals("abc", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getId());
        assertEquals("Loreal Kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getName());
        assertEquals("kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getType());
        assertEquals(userId, videoDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", videoDocArgumentCaptor.getValue().getUserRef().getUsername());
    }

    @Test
    void save_PassingObjectToUserActivity() {
        ObjectId userId = new ObjectId();
        VideoDoc videoDocMock = new VideoDoc("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef(userId, "shabana"));
        ObjectId videoId = new ObjectId();
        videoDocMock.setId(videoId);
        videoDocMock.setDatePosted(LocalDateTime.now());
        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(videoDocMock);
        when(this.videoRepositoryMock.save(any())).thenReturn(videoDocMock);
        this.videoImplSpy.save(null);
        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.userActivityMock, times(1)).addVideo(videoDocArgumentCaptor.capture());
        assertEquals(videoId, videoDocArgumentCaptor.getValue().getId());
        assertNotNull(videoDocArgumentCaptor.getValue().getDatePosted());
        assertEquals("review", videoDocArgumentCaptor.getValue().getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDocArgumentCaptor.getValue().getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDocArgumentCaptor.getValue().getDescription());
        assertEquals("vQ765gh", videoDocArgumentCaptor.getValue().getSourceId());
        assertEquals(2, videoDocArgumentCaptor.getValue().getProductRefList().size());
        assertEquals("abc", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getId());
        assertEquals("Loreal Kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getName());
        assertEquals("kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getType());
        assertEquals(userId, videoDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", videoDocArgumentCaptor.getValue().getUserRef().getUsername());
    }

    /*@Test
    void findById_docFoundResponse() {
        VideoDoc videoDocMocked = new VideoDoc();
        videoDocMocked.setTitle("THis is a test discusion title");
        videoDocMocked.setLikes(10);
        videoDocMocked.setViews(20);
        videoDocMocked.setActive(true);
        videoDocMocked.setId(ObjectId.get());

        Optional<DiscussionTopicDoc> discussionTopicDocOptional = Optional.of(videoDocMocked);
        when(this.discussionTopicRepository.findById(any())).thenReturn(discussionTopicDocOptional);
        when(this.discussionTopicDtoDocMapperMocked.toDto(videoDocMocked)).thenReturn(discussionTopicDtoDocMapper.toDto(videoDocMocked));
        DiscussionTopicDto discussionTopicDtoReturned = discussionTopicImplSpy.findById(ObjectId.get().toHexString());

        assertEquals("THis is a test discusion title", discussionTopicDtoReturned.getTitle());
        assertEquals("10", discussionTopicDtoReturned.getLikes());
        assertEquals("20", discussionTopicDtoReturned.getViews());
    }*/

    @Test
    void findById_testPassingEntityToRepository() {
        ObjectId videoId = ObjectId.get();
        when(this.videoRepositoryMock.findByIdAndActiveTrue(videoId)).thenReturn(Optional.of(new VideoDoc()));
        videoImplSpy.findById(videoId.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(videoRepositoryMock, times(1)).findByIdAndActiveTrue(idArgumentCaptor.capture());
        assertEquals(videoId, idArgumentCaptor.getValue());
    }

    @Test
    void getLatestThree_testPassingEntityToRepository() {
        ObjectId userId = ObjectId.get();
        when(this.videoRepositoryMock.getLatestThreeVideosByUser(userId)).thenReturn(null);
        ObjectId videoId = new ObjectId();
        Assertions.assertThrows(VideoNotFoundException.class, () -> videoImplSpy.findById(videoId.toHexString()));
        this.videoImplSpy.getLatestThreeVideoThumbnails(userId.toHexString());
        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(videoRepositoryMock, times(1)).getLatestThreeVideosByUser(userIdArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
    }

    @Test
    void incrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        String videoID = ObjectId.get().toHexString();
        videoLikeUnlikeDto.setVideoId(videoID);
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        when(this.videoRepositoryMock.saveVideoLikes(videoLikeUnlikeDto)).thenReturn("123");
        videoImplSpy.incrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoRepositoryMock, times(1)).saveVideoLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals(videoID, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void incrementVideoLikes_PassingObjectToUserActivity() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        String videoID = ObjectId.get().toHexString();
        videoLikeUnlikeDto.setVideoId(videoID);
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        when(this.userActivityMock.addVideoLike(videoLikeUnlikeDto)).thenReturn(null);
        this.videoImplSpy.incrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(userActivityMock, times(1)).addVideoLike(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals(videoID, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void decrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        String videoID = ObjectId.get().toHexString();
        videoLikeUnlikeDto.setVideoId(videoID);
        when(this.videoRepositoryMock.deleteVideoLikes(videoLikeUnlikeDto)).thenReturn("123");
        videoImplSpy.decrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoRepositoryMock, times(1)).deleteVideoLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals(videoID, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals(null, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void decrementLikes_testPassingToUserActivity() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        String videoID = ObjectId.get().toHexString();
        videoLikeUnlikeDto.setVideoId(videoID);
        when(this.userActivityMock.deleteVideoLike(videoLikeUnlikeDto)).thenReturn(null);
        videoImplSpy.decrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(userActivityMock, times(1)).deleteVideoLike(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals(videoID, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
    }

    @Test
    void insertComment_testPassingArgumentsToVideoRespository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        when(this.videoRepositoryMock.insertComment(any(), any())).thenReturn(null);
        ObjectId userId = new ObjectId();
        when(this.videoCommentMapperMock.toDoc(videoCommentDto)).thenReturn(new VideoComment(new UserRef(userId, "priyanka11"), null, "This is a comment to test videoCommentMapper toDto()"));
        ObjectId postId = new ObjectId();
        when(this.postRefMapperMock.toDoc(postRefDtoMocked)).thenReturn(new PostRef(postId, "This is a test post title", "guide", "video", null));
        videoImplSpy.insertComment(videoCommentDto);
        ArgumentCaptor<VideoComment> videoCommentArgumentCaptor = ArgumentCaptor.forClass(VideoComment.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(videoRepositoryMock, times(1)).insertComment(postRefArgumentCaptor.capture(), videoCommentArgumentCaptor.capture());
        assertEquals(postId, postRefArgumentCaptor.getValue().getId());
        assertNotNull(videoCommentArgumentCaptor.getValue().getId());
        assertEquals(userId, videoCommentArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("priyanka11", videoCommentArgumentCaptor.getValue().getUserRef().getUsername());
        assertEquals("This is a comment to test videoCommentMapper toDto()", videoCommentArgumentCaptor.getValue().getComment());
    }

    @Test
    void insertCommentUpdate_updateMethodInVideoRespositoryCalled() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        ObjectId commentId = ObjectId.get();
        videoCommentDto.setId(commentId.toHexString());
        when(this.videoRepositoryMock.updateComment(any())).thenReturn(null);
        videoImplSpy.insertComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoRepositoryMock, times(1)).updateComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals(commentId.toHexString(), videoCommentDtoArgumentCaptor.getValue().getId());
    }

    @Test
    void insertComment_testPassingArgumentsToUserActivity() {
        ObjectId videoId = ObjectId.get();
        PostRefDto postRefDtoMocked = new PostRefDto(videoId.toHexString(), "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        when(this.userActivityMock.addVideoComment(any(), any())).thenReturn(null);
        LocalDateTime datePosted = LocalDateTime.parse("2020-02-04 19:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ObjectId userId = new ObjectId();
        VideoComment videoComment = new VideoComment(new UserRef(userId, "priyanka11"), datePosted, "This is a comment to test videoCommentMapper toDto()");
        String commentId = ObjectId.get().toHexString();
        videoComment.setId(commentId);
        when(this.videoCommentMapperMock.toDto(any())).thenReturn(new VideoCommentDto());
        when(this.videoCommentMapperMock.toDoc(videoCommentDto)).thenReturn(videoComment);
        PostRef postRef = new PostRef(videoId, "This is a test post title", "guide", "video", null);
        when(this.postRefMapperMock.toDoc(postRefDtoMocked)).thenReturn(postRef);
        videoImplSpy.insertComment(videoCommentDto);
        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<CommentRef> commentRefArgumentCaptor = ArgumentCaptor.forClass(CommentRef.class);
        verify(userActivityMock, times(1)).addVideoComment(userIdArgumentCaptor.capture(), commentRefArgumentCaptor.capture());
        assertEquals(commentId, commentRefArgumentCaptor.getValue().getId());
        assertEquals("This is a comment to test videoCommentMapper toDto()", commentRefArgumentCaptor.getValue().getComment());
        assertEquals(postRef, commentRefArgumentCaptor.getValue().getPostRef());
        assertEquals(datePosted, commentRefArgumentCaptor.getValue().getDatePosted());
    }

    @Test
    void deleteComment_testPassingArgumentsToVideoRespository() {
        String videoID = ObjectId.get().toHexString();
        PostRefDto postRefDtoMocked = new PostRefDto(videoID, null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", null), null);
        videoCommentDto.setPostRefDto(postRefDtoMocked);

        videoCommentDto.setId(videoID);
        when(this.videoRepositoryMock.deleteComment(any())).thenReturn(true);
        videoImplSpy.deleteComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoRepositoryMock, times(1)).deleteComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals(videoID, videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals(videoID, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }

    @Test
    void deleteComment_testPassingArgumentsToUserActivity() {
        String videoID = ObjectId.get().toHexString();
        PostRefDto postRefDtoMocked = new PostRefDto(videoID, null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", null), null);
        videoCommentDto.setPostRefDto(postRefDtoMocked);

        videoCommentDto.setId(videoID);
        when(this.videoRepositoryMock.deleteComment(any())).thenReturn(true);
        when(this.userActivityMock.deleteVideoComment(any())).thenReturn(null);
        videoImplSpy.deleteComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(userActivityMock, times(1)).deleteVideoComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals(videoID, videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals(videoID, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }

    @Test
    void saveCommentLike_testPassingEntityToRepository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("10");
        when(this.videoRepositoryMock.saveCommentLike(any())).thenReturn("10");
        videoImplSpy.saveCommentLike(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoRepositoryMock, times(1)).saveCommentLike(videoCommentDtoArgumentCaptor.capture());
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
    void saveCommentLike_testPassingToUserActivity() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", "guide", "video", null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("10");
        when(this.videoRepositoryMock.saveCommentLike(any())).thenReturn("11");
        when(this.userActivityMock.addCommentLike(any())).thenReturn(null);
        videoImplSpy.saveCommentLike(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(userActivityMock, times(1)).addCommentLike(videoCommentDtoArgumentCaptor.capture());
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
    void deleteCommentLike_testPassingEntityToRepository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", null), null);
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("10");
        when(this.videoRepositoryMock.deleteCommentLike(any())).thenReturn("11");
        videoImplSpy.deleteCommentLike(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoRepositoryMock, times(1)).deleteCommentLike(videoCommentDtoArgumentCaptor.capture());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals("v123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }

    @Test
    void deleteCommentLike_testPassingToUserActivity() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", null, null, null, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto("u123", null), null);
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("12");
        when(this.videoRepositoryMock.deleteCommentLike(any())).thenReturn("11");
        when(this.userActivityMock.deleteCommentLike(any())).thenReturn(null);
        videoImplSpy.deleteCommentLike(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(userActivityMock, times(1)).deleteCommentLike(videoCommentDtoArgumentCaptor.capture());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals("v123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }


}