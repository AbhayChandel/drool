package com.hexlindia.drool.video.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocField;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoRepository;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import com.hexlindia.drool.video.dto.mapper.VideoCommentMapper;
import com.hexlindia.drool.video.dto.mapper.VideoDocDtoMapper;
import com.hexlindia.drool.video.dto.mapper.VideoThumbnailDataMapper;
import com.hexlindia.drool.video.exception.VideoNotFoundException;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
        VideoDoc videoDocMock = new VideoDoc(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef(userId, "shabana"));
        ObjectId videoId = ObjectId.get();
        videoDocMock.setId(videoId);

        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(videoDocMock);
        when(this.videoRepositoryMock.save(any())).thenReturn(videoDocMock);
        VideoDto videoDto = new VideoDto();
        this.videoImplSpy.saveOrUpdate(videoDto);

        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.videoRepositoryMock, times(1)).save(videoDocArgumentCaptor.capture());
        assertEquals(PostType.review, videoDocArgumentCaptor.getValue().getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDocArgumentCaptor.getValue().getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDocArgumentCaptor.getValue().getDescription());
        assertEquals("vQ765gh", videoDocArgumentCaptor.getValue().getSourceId());
        assertEquals(2, videoDocArgumentCaptor.getValue().getProductRefList().size());
        assertEquals("abc", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getId());
        assertEquals("Loreal Kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getName());
        assertEquals("kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getType());
        assertEquals(userId, videoDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", videoDocArgumentCaptor.getValue().getUserRef().getUsername());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(videoId, postRefArgumentCaptor.getValue().getId());
        assertEquals(PostType.review, postRefArgumentCaptor.getValue().getType());
        assertEquals(PostMedium.video, postRefArgumentCaptor.getValue().getMedium());

        ArgumentCaptor<VideoDoc> videoDocArgumentCaptorActivityFeed = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.activityFeedMock, times(1)).addVideo(videoDocArgumentCaptorActivityFeed.capture());
        VideoDoc videoDocActivityFeed = videoDocArgumentCaptorActivityFeed.getValue();
        assertEquals(videoId, videoDocActivityFeed.getId());
        assertEquals(PostType.review, videoDocActivityFeed.getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDocActivityFeed.getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDocActivityFeed.getDescription());
        assertEquals("vQ765gh", videoDocActivityFeed.getSourceId());
        assertEquals(2, videoDocActivityFeed.getProductRefList().size());
        assertEquals("abc", videoDocActivityFeed.getProductRefList().get(0).getId());
        assertEquals("Loreal Kajal", videoDocActivityFeed.getProductRefList().get(0).getName());
        assertEquals("kajal", videoDocActivityFeed.getProductRefList().get(0).getType());
        assertEquals(userId, videoDocActivityFeed.getUserRef().getId());
        assertEquals("shabana", videoDocActivityFeed.getUserRef().getUsername());

    }

    @Test
    void update_PassingObjectToUserActivity() {
        ObjectId userId = new ObjectId();
        VideoDoc videoDocMock = new VideoDoc(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Loreal Kajal", "kajal"), new ProductRef("xyz", "Nykaa Kajal", "kajal")),
                new UserRef(userId, "shabana"));
        ObjectId videoId = ObjectId.get();
        videoDocMock.setId(videoId);

        when(this.videoDocDtoMapperMock.toDoc(any())).thenReturn(videoDocMock);
        when(this.videoRepositoryMock.updateVideo(any())).thenReturn(true);
        VideoDto videoDto = new VideoDto();
        videoDto.setId("ad");
        this.videoImplSpy.saveOrUpdate(videoDto);

        ArgumentCaptor<VideoDoc> videoDocArgumentCaptor = ArgumentCaptor.forClass(VideoDoc.class);
        verify(this.videoRepositoryMock, times(1)).updateVideo(videoDocArgumentCaptor.capture());
        assertEquals(PostType.review, videoDocArgumentCaptor.getValue().getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDocArgumentCaptor.getValue().getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDocArgumentCaptor.getValue().getDescription());
        assertEquals("vQ765gh", videoDocArgumentCaptor.getValue().getSourceId());
        assertEquals(2, videoDocArgumentCaptor.getValue().getProductRefList().size());
        assertEquals("abc", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getId());
        assertEquals("Loreal Kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getName());
        assertEquals("kajal", videoDocArgumentCaptor.getValue().getProductRefList().get(0).getType());
        assertEquals(userId, videoDocArgumentCaptor.getValue().getUserRef().getId());
        assertEquals("shabana", videoDocArgumentCaptor.getValue().getUserRef().getUsername());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).update(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(videoId, postRefArgumentCaptor.getValue().getId());
        assertEquals(PostType.review, postRefArgumentCaptor.getValue().getType());
        assertEquals(PostMedium.video, postRefArgumentCaptor.getValue().getMedium());

        ArgumentCaptor<ObjectId> postIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<FeedDocField> feedDocFieldArgumentCaptor = ArgumentCaptor.forClass(FeedDocField.class);
        ArgumentCaptor<String> valueArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.activityFeedMock, times(1)).setField(postIdArgumentCaptor.capture(), feedDocFieldArgumentCaptor.capture(), valueArgumentCaptor.capture());
        assertEquals(videoDocMock.getId(), postIdArgumentCaptor.getValue());
        assertEquals(FeedDocField.title, feedDocFieldArgumentCaptor.getValue());
        assertEquals("L'oreal Collosal Kajal Review", valueArgumentCaptor.getValue());
    }

    @Test
    void deleteVideo() {
        ObjectId videoId = ObjectId.get();
        VideoDto videoDtoMocked = new VideoDto();
        videoDtoMocked.setId(videoId.toHexString());
        ObjectId userId = new ObjectId();
        videoDtoMocked.setUserRefDto(new UserRefDto(userId.toHexString(), "priyanka"));
        videoDtoMocked.setType(PostType.guide);

        DeleteResult deleteResultMocked = DeleteResult.acknowledged(1);
        when(this.videoRepositoryMock.deleteVideo(any())).thenReturn(deleteResultMocked);
        this.videoImplSpy.delete(videoDtoMocked);

        ArgumentCaptor<ObjectId> idArgumentCaptorRepo = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.videoRepositoryMock, times(1)).deleteVideo(idArgumentCaptorRepo.capture());
        assertEquals(videoId, idArgumentCaptorRepo.getValue());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).delete(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(videoId, postRefArgumentCaptor.getValue().getId());
        assertEquals(PostType.guide, postRefArgumentCaptor.getValue().getType());
        assertEquals(PostMedium.video, postRefArgumentCaptor.getValue().getMedium());

        ArgumentCaptor<ObjectId> idArgumentCaptorActivityFeed = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.activityFeedMock, times(1)).delete(idArgumentCaptorActivityFeed.capture());
        assertEquals(videoId, idArgumentCaptorActivityFeed.getValue());
    }

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
        ObjectId userId = ObjectId.get();
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        String videoID = ObjectId.get().toHexString();
        videoLikeUnlikeDto.setVideoId(videoID);
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        when(this.videoRepositoryMock.saveVideoLikes(videoLikeUnlikeDto)).thenReturn("123");
        videoImplSpy.incrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoRepositoryMock, times(1)).saveVideoLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals(userId.toHexString(), videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals(videoID, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void incrementVideoLikes_PassingObjectToUserActivity() {
        ObjectId userId = ObjectId.get();
        ActionType actionType = ActionType.like;
        ObjectId postId = ObjectId.get();

        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        videoLikeUnlikeDto.setVideoId(postId.toHexString());
        videoLikeUnlikeDto.setVideoTitle("Testing adding video like");
        videoLikeUnlikeDto.setPostType(PostType.guide);
        videoLikeUnlikeDto.setPostMedium(PostMedium.video);

        when(this.userActivityMock.add(any(), any(), any())).thenReturn(null);
        this.videoImplSpy.incrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(actionType, actionTypeArgumentCaptor.getValue());
        assertEquals(postId, postRefArgumentCaptor.getValue().getId());
        assertEquals("Testing adding video like", postRefArgumentCaptor.getValue().getTitle());
        assertEquals(PostType.guide, postRefArgumentCaptor.getValue().getType());
        assertEquals(PostMedium.video, postRefArgumentCaptor.getValue().getMedium());
    }

    @Test
    void decrementLikes_testPassingEntityToRepository() {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        ObjectId userId = ObjectId.get();
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        String videoID = ObjectId.get().toHexString();
        videoLikeUnlikeDto.setVideoId(videoID);
        when(this.videoRepositoryMock.deleteVideoLikes(videoLikeUnlikeDto)).thenReturn("123");
        videoImplSpy.decrementVideoLikes(videoLikeUnlikeDto);
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(videoRepositoryMock, times(1)).deleteVideoLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals(userId.toHexString(), videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals(videoID, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals(null, videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void decrementLikes_testPassingToUserActivity() {
        ObjectId userId = ObjectId.get();
        ObjectId postId = ObjectId.get();

        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId(userId.toHexString());
        videoLikeUnlikeDto.setVideoId(postId.toHexString());
        videoLikeUnlikeDto.setVideoTitle("Testing adding video like");
        videoLikeUnlikeDto.setPostType(PostType.guide);
        videoLikeUnlikeDto.setPostMedium(PostMedium.video);

        when(this.userActivityMock.delete(any(), any(), any())).thenReturn(null);
        videoImplSpy.decrementVideoLikes(videoLikeUnlikeDto);

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).delete(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.like, actionTypeArgumentCaptor.getValue());
        assertEquals(postId, postRefArgumentCaptor.getValue().getId());
    }

    @Test
    void insertComment_testPassingArgumentsToVideoRespository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", PostType.guide, PostMedium.video, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto("u123", "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        when(this.videoRepositoryMock.insertComment(any(), any())).thenReturn(null);
        ObjectId userId = new ObjectId();
        when(this.videoCommentMapperMock.toDoc(videoCommentDto)).thenReturn(new VideoComment(new UserRef(userId, "priyanka11"), null, "This is a comment to test videoCommentMapper toDto()"));
        ObjectId postId = new ObjectId();
        when(this.postRefMapperMock.toDoc(postRefDtoMocked)).thenReturn(new PostRef(postId, "This is a test post title", PostType.guide, PostMedium.video, null));
        videoImplSpy.insertOrUpdateComment(videoCommentDto);
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
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", PostType.guide, PostMedium.video, null);
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto(ObjectId.get().toHexString(), "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        ObjectId commentId = ObjectId.get();
        videoCommentDto.setId(commentId.toHexString());
        when(this.videoRepositoryMock.updateComment(any())).thenReturn(videoCommentDto);
        videoImplSpy.insertOrUpdateComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoRepositoryMock, times(1)).updateComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals(commentId.toHexString(), videoCommentDtoArgumentCaptor.getValue().getId());
    }

    @Test
    void insertComment_testPassingArgumentsToUserActivity() {
        ObjectId videoId = ObjectId.get();
        PostRef postRef = new PostRef(videoId, "This is a test post title", PostType.guide, PostMedium.video, null);
        when(this.postRefMapperMock.toDoc(any())).thenReturn(postRef);

        ObjectId userId = new ObjectId();
        VideoComment videoComment = new VideoComment(new UserRef(userId, "priyanka11"), null, "This is a comment to test videoCommentMapper toDto()");
        ObjectId postId = ObjectId.get();
        videoComment.setId(postId);
        when(this.videoCommentMapperMock.toDoc(any())).thenReturn(videoComment);

        when(this.videoCommentMapperMock.toDto(any())).thenReturn(new VideoCommentDto());
        when(this.userActivityMock.add(any(), any(), any())).thenReturn(null);

        this.videoImplSpy.insertOrUpdateComment(new VideoCommentDto());

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(postId, postRefArgumentCaptor.getValue().getId());
        assertEquals(postRef, postRefArgumentCaptor.getValue().getParentPost());
    }

    @Test
    void deleteComment_testPassingArgumentsToVideoRespository() {
        String videoID = ObjectId.get().toHexString();
        PostRefDto postRefDtoMocked = new PostRefDto(videoID, null, null, null, null);
        ObjectId userId = ObjectId.get();
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto(userId.toHexString(), null), null);
        videoCommentDto.setPostRefDto(postRefDtoMocked);

        videoCommentDto.setId(videoID);
        when(this.videoRepositoryMock.deleteComment(any())).thenReturn(true);
        videoImplSpy.deleteComment(videoCommentDto);
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(videoRepositoryMock, times(1)).deleteComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals(videoID, videoCommentDtoArgumentCaptor.getValue().getId());
        assertEquals(videoID, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals(userId.toHexString(), videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
    }

    @Test
    void deleteComment_testPassingArgumentsToUserActivity() {
        ObjectId videoId = ObjectId.get();
        PostRefDto postRefDtoMocked = new PostRefDto(videoId.toHexString(), null, null, null, null);
        ObjectId userId = ObjectId.get();
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto(userId.toHexString(), null), null);
        videoCommentDto.setPostRefDto(postRefDtoMocked);
        ObjectId commentId = ObjectId.get();
        videoCommentDto.setId(commentId.toHexString());

        when(this.videoRepositoryMock.deleteComment(any())).thenReturn(true);
        when(this.userActivityMock.delete(any(), any(), any())).thenReturn(null);
        videoImplSpy.deleteComment(videoCommentDto);

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).delete(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.post, actionTypeArgumentCaptor.getValue());
        assertEquals(commentId, postRefArgumentCaptor.getValue().getId());
    }

    @Test
    void saveCommentLike_testPassingEntityToRepository() {
        PostRefDto postRefDtoMocked = new PostRefDto("v123", "This is a test post title", PostType.guide, PostMedium.video, null);
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
        assertEquals(PostType.guide, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getType());
        assertEquals(PostMedium.video, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getMedium());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("priyanka11", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
        assertEquals("This is a comment passed to VideoTemplateRespository", videoCommentDtoArgumentCaptor.getValue().getComment());
    }

    @Test
    void saveCommentLike_testPassingToUserActivity() {

        PostRef parentPostRef = new PostRef(ObjectId.get(), "This is a test post title", PostType.guide, PostMedium.video, null);
        when(this.postRefMapperMock.toDoc(any())).thenReturn(parentPostRef);

        ObjectId userId = ObjectId.get();
        VideoCommentDto videoCommentDto = new VideoCommentDto(null, new UserRefDto(userId.toHexString(), "priyanka11"), "This is a comment passed to VideoTemplateRespository");
        ObjectId postId = ObjectId.get();
        videoCommentDto.setId(postId.toHexString());
        videoCommentDto.setLikes("10");
        when(this.videoRepositoryMock.saveCommentLike(any())).thenReturn("11");

        when(this.userActivityMock.add(any(), any(), any())).thenReturn(null);
        videoImplSpy.saveCommentLike(videoCommentDto);

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(userActivityMock, times(1)).add(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());
        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.like, actionTypeArgumentCaptor.getValue());
        assertEquals(parentPostRef, postRefArgumentCaptor.getValue().getParentPost());
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
        ObjectId userId = ObjectId.get();
        VideoCommentDto videoCommentDto = new VideoCommentDto(postRefDtoMocked, new UserRefDto(userId.toHexString(), null), null);
        ObjectId commentId = ObjectId.get();
        videoCommentDto.setId(commentId.toHexString());
        videoCommentDto.setLikes("12");

        when(this.videoRepositoryMock.deleteCommentLike(any())).thenReturn("11");
        when(this.userActivityMock.delete(any(), any(), any())).thenReturn(null);
        videoImplSpy.deleteCommentLike(videoCommentDto);

        ArgumentCaptor<ObjectId> userIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ActionType> actionTypeArgumentCaptor = ArgumentCaptor.forClass(ActionType.class);
        ArgumentCaptor<PostRef> postRefArgumentCaptor = ArgumentCaptor.forClass(PostRef.class);
        verify(this.userActivityMock, times(1)).delete(userIdArgumentCaptor.capture(), actionTypeArgumentCaptor.capture(), postRefArgumentCaptor.capture());

        assertEquals(userId, userIdArgumentCaptor.getValue());
        assertEquals(ActionType.like, actionTypeArgumentCaptor.getValue());
        assertEquals(commentId, postRefArgumentCaptor.getValue().getId());
    }


}