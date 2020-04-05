package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.business.api.usecase.AspectVotingDetails;
import com.hexlindia.drool.product.business.api.usecase.Brand;
import com.hexlindia.drool.product.business.api.usecase.BrandEvaluation;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.*;
import com.hexlindia.drool.product.dto.mapper.AspectTemplateMapper;
import com.hexlindia.drool.product.dto.mapper.ReviewMapper;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.VideoDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductReviewImplTest {

    private ProductReview productReviewSpy;

    @Mock
    private Brand brandMock;

    @Mock
    private ProductReviewRepository productReviewRepositoryMock;

    @Mock
    private AspectTemplateMapper aspectTemplateMapperMock;

    @Mock
    private ReviewMapper reviewMapperMock;

    @Mock
    private AspectVotingDetails aspectVotingDetailsMock;

    @Mock
    private Video videoMock;

    @Mock
    private BrandEvaluation brandEvaluation;

    @Mock
    private UserActivity userActivityMock;

    @BeforeEach
    void setUp() {
        this.productReviewSpy = Mockito.spy(new ProductReviewImpl(brandMock, productReviewRepositoryMock, aspectTemplateMapperMock, reviewMapperMock, videoMock, aspectVotingDetailsMock, brandEvaluation, userActivityMock));
    }

    @Test
    void saveTextReview_passedToRepositoryLayer() {
        ReviewDto reviewDto = new ReviewDto();

        ReviewDoc reviewDocMocked = new ReviewDoc();
        reviewDocMocked.setReviewType(ReviewType.text);
        reviewDocMocked.setRecommendation("1");
        reviewDocMocked.setDetailedReview("THis is a details text review");
        reviewDocMocked.setReviewSummary("This is text review summary");

        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId("abc");
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Retro", "Bohemian"));
        AspectVotingDto aspectVotingDtoOcassion = new AspectVotingDto();
        aspectVotingDtoOcassion.setAspectId("def");
        aspectVotingDtoOcassion.setSelectedOptions(Arrays.asList("Wedding", "Cocktail"));
        List<AspectVotingDto> aspectVotingDtoList = new ArrayList<>();
        aspectVotingDtoList.add(aspectVotingDtoOcassion);
        aspectVotingDtoList.add(aspectVotingDtoStyle);
        reviewDto.setAspectVotingDtoList(aspectVotingDtoList);
        reviewDto.setUserRefDto(new UserRefDto("u123", "username123"));

        BrandCriterionRatingDto brandCriterionRatingDtoTrendy = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrendy.setName("Trendy");
        brandCriterionRatingDtoTrendy.setRating(4);
        BrandCriterionRatingDto brandCriterionRatingDtoTrustable = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrustable.setName("Trustable");
        brandCriterionRatingDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto = new BrandCriteriaRatingsDetailsDto(null, null, Arrays.asList(brandCriterionRatingDtoTrendy, brandCriterionRatingDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDto.setBrandCriteriaRatingsDetailsDto(brandCriteriaRatingsDetailsDto);

        when(this.reviewMapperMock.toReviewDoc(reviewDto)).thenReturn(reviewDocMocked);
        ObjectId mockedObjectId = new ObjectId();
        reviewDto.setProductRefDto(new ProductRefDto(mockedObjectId.toHexString(), "MockedProduct", "MOckedCategory"));
        when(this.productReviewRepositoryMock.save(reviewDocMocked, mockedObjectId, aspectVotingDtoList)).thenReturn(reviewDocMocked);
        ObjectId mockedBrandRatingId = new ObjectId();
        when(this.brandEvaluation.saveCriteriaRatings(brandCriteriaRatingsDetailsDto)).thenReturn(mockedBrandRatingId);
        this.productReviewSpy.save(reviewDto);
        ArgumentCaptor<ReviewDoc> reviewDocArgumentCaptor = ArgumentCaptor.forClass(ReviewDoc.class);
        ArgumentCaptor<ObjectId> productIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<List<AspectVotingDto>> aspectPrefArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(this.productReviewRepositoryMock, times(1)).save(reviewDocArgumentCaptor.capture(), productIdArgumentCaptor.capture(), aspectPrefArgumentCaptor.capture());
        assertEquals(ReviewType.text, reviewDocArgumentCaptor.getValue().getReviewType());
        assertEquals("1", reviewDocArgumentCaptor.getValue().getRecommendation());
        assertEquals("THis is a details text review", reviewDocArgumentCaptor.getValue().getDetailedReview());
        assertEquals("This is text review summary", reviewDocArgumentCaptor.getValue().getReviewSummary());
        assertEquals(mockedObjectId, productIdArgumentCaptor.getValue());
        assertEquals("abc", aspectPrefArgumentCaptor.getValue().get(1).getAspectId());
        assertEquals("Retro", aspectPrefArgumentCaptor.getValue().get(1).getSelectedOptions().get(0));
        assertEquals("Bohemian", aspectPrefArgumentCaptor.getValue().get(1).getSelectedOptions().get(1));
        assertEquals("def", aspectPrefArgumentCaptor.getValue().get(0).getAspectId());
        assertEquals("Wedding", aspectPrefArgumentCaptor.getValue().get(0).getSelectedOptions().get(0));
        assertEquals("Cocktail", aspectPrefArgumentCaptor.getValue().get(0).getSelectedOptions().get(1));
    }

    @Test
    void saveVideReview_passedToVideoBusiness() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewType(ReviewType.video);
        reviewDto.setProductRefDto(new ProductRefDto(ObjectId.get().toHexString(), null, null));

        ReviewDoc reviewDocMocked = new ReviewDoc();
        VideoDto videoDto = new VideoDto();
        ObjectId videoMockedId = ObjectId.get();
        videoDto.setId(videoMockedId.toHexString());
        videoDto.setType("review");
        videoDto.setActive(true);
        videoDto.setTitle("The is a mocked video review");
        videoDto.setDescription("this is a mocked video review description");
        videoDto.setSourceId("werlkj");
        ProductRefDto productRefDto = new ProductRefDto("p123", "Carolina Herrera 212", "fragrance");
        videoDto.setProductRefDtoList(Arrays.asList(productRefDto));
        UserRefDto userRefDto = new UserRefDto("u1123", "User123");
        videoDto.setUserRefDto(userRefDto);
        reviewDto.setVideoDto(videoDto);

        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId("abc");
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Retro", "Bohemian"));
        AspectVotingDto aspectVotingDtoOcassion = new AspectVotingDto();
        aspectVotingDtoOcassion.setAspectId("def");
        aspectVotingDtoOcassion.setSelectedOptions(Arrays.asList("Wedding", "Cocktail"));
        List<AspectVotingDto> aspectVotingDtoList = new ArrayList<>();
        aspectVotingDtoList.add(aspectVotingDtoOcassion);
        aspectVotingDtoList.add(aspectVotingDtoStyle);
        reviewDto.setAspectVotingDtoList(aspectVotingDtoList);

        BrandCriterionRatingDto brandCriterionRatingDtoTrendy = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrendy.setName("Trendy");
        brandCriterionRatingDtoTrendy.setRating(4);
        BrandCriterionRatingDto brandCriterionRatingDtoTrustable = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrustable.setName("Trustable");
        brandCriterionRatingDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto = new BrandCriteriaRatingsDetailsDto(null, null, Arrays.asList(brandCriterionRatingDtoTrendy, brandCriterionRatingDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDto.setBrandCriteriaRatingsDetailsDto(brandCriteriaRatingsDetailsDto);

        when(this.reviewMapperMock.toReviewDoc(reviewDto)).thenReturn(reviewDocMocked);
        ObjectId mockedProductId = new ObjectId();
        reviewDto.setProductRefDto(new ProductRefDto(mockedProductId.toHexString(), "MockedProduct", "MOckedCategory"));
        reviewDto.setUserRefDto(userRefDto);
        ObjectId mockedBrandRatingId = new ObjectId();
        when(this.brandEvaluation.saveCriteriaRatings(brandCriteriaRatingsDetailsDto)).thenReturn(mockedBrandRatingId);
        when(this.productReviewRepositoryMock.save(any(), any(), anyList())).thenReturn(reviewDocMocked);
        when(this.videoMock.save(videoDto)).thenReturn(videoDto);
        this.productReviewSpy.save(reviewDto);
        ArgumentCaptor<VideoDto> videoDtoArgumentCaptor = ArgumentCaptor.forClass(VideoDto.class);

        verify(this.videoMock, times(1)).save(videoDtoArgumentCaptor.capture());
        assertEquals(videoMockedId.toHexString(), videoDtoArgumentCaptor.getValue().getId());
        assertEquals("review", videoDtoArgumentCaptor.getValue().getType());
        assertTrue(videoDtoArgumentCaptor.getValue().isActive());
        assertEquals("The is a mocked video review", videoDtoArgumentCaptor.getValue().getTitle());
        assertEquals("this is a mocked video review description", videoDtoArgumentCaptor.getValue().getDescription());
        assertEquals("werlkj", videoDtoArgumentCaptor.getValue().getSourceId());
        assertEquals(mockedProductId.toHexString(), videoDtoArgumentCaptor.getValue().getProductRefDtoList().get(0).getId());
        assertEquals("MockedProduct", videoDtoArgumentCaptor.getValue().getProductRefDtoList().get(0).getName());
        assertEquals("MOckedCategory", videoDtoArgumentCaptor.getValue().getProductRefDtoList().get(0).getType());
        assertEquals("u1123", videoDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("User123", videoDtoArgumentCaptor.getValue().getUserRefDto().getUsername());


    }

    @Test
    void saveVideoReview_passedToRepositoryLayer() {
        ReviewDoc reviewDocMocked = new ReviewDoc();
        reviewDocMocked.setReviewType(ReviewType.video);
        reviewDocMocked.setRecommendation("2");
        ObjectId mockedVideoId = new ObjectId();
        reviewDocMocked.setVideoId(mockedVideoId);

        ObjectId mockedProductId = new ObjectId();
        ReviewDto reviewDtoMocked = new ReviewDto();
        reviewDtoMocked.setProductRefDto(new ProductRefDto(mockedProductId.toHexString(), "Colossal Kajal", "Kajal"));
        reviewDtoMocked.setUserRefDto(new UserRefDto("1", "shabana"));

        when(this.reviewMapperMock.toReviewDoc(any())).thenReturn(reviewDocMocked);

        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId("abc");
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Retro", "Bohemian"));
        AspectVotingDto aspectVotingDtoOcassion = new AspectVotingDto();
        aspectVotingDtoOcassion.setAspectId("def");
        aspectVotingDtoOcassion.setSelectedOptions(Arrays.asList("Wedding", "Cocktail"));
        List<AspectVotingDto> aspectVotingDtoList = new ArrayList<>();
        aspectVotingDtoList.add(aspectVotingDtoOcassion);
        aspectVotingDtoList.add(aspectVotingDtoStyle);

        reviewDtoMocked.setAspectVotingDtoList(aspectVotingDtoList);

        BrandCriterionRatingDto brandCriterionRatingDtoTrendy = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrendy.setName("Trendy");
        brandCriterionRatingDtoTrendy.setRating(4);
        BrandCriterionRatingDto brandCriterionRatingDtoTrustable = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrustable.setName("Trustable");
        brandCriterionRatingDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto = new BrandCriteriaRatingsDetailsDto(null, null, Arrays.asList(brandCriterionRatingDtoTrendy, brandCriterionRatingDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDtoMocked.setBrandCriteriaRatingsDetailsDto(brandCriteriaRatingsDetailsDto);

        when(this.productReviewRepositoryMock.save(reviewDocMocked, mockedProductId, aspectVotingDtoList)).thenReturn(reviewDocMocked);
        ObjectId mockedBrandRatingId = new ObjectId();
        when(this.brandEvaluation.saveCriteriaRatings(brandCriteriaRatingsDetailsDto)).thenReturn(mockedBrandRatingId);
        this.productReviewSpy.save(reviewDtoMocked);
        ArgumentCaptor<ReviewDoc> reviewDocArgumentCaptor = ArgumentCaptor.forClass(ReviewDoc.class);
        ArgumentCaptor<ObjectId> productIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<List<AspectVotingDto>> aspectPrefArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(this.productReviewRepositoryMock, times(1)).save(reviewDocArgumentCaptor.capture(), productIdArgumentCaptor.capture(), aspectPrefArgumentCaptor.capture());
        assertEquals(ReviewType.video, reviewDocArgumentCaptor.getValue().getReviewType());
        assertEquals("2", reviewDocArgumentCaptor.getValue().getRecommendation());
        assertEquals(mockedVideoId, reviewDocArgumentCaptor.getValue().getVideoId());
        assertEquals(mockedProductId, productIdArgumentCaptor.getValue());
    }
}