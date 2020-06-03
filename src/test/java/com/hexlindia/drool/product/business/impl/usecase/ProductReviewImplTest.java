package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.business.api.usecase.AspectVotingDetails;
import com.hexlindia.drool.product.business.api.usecase.BrandRating;
import com.hexlindia.drool.product.business.api.usecase.BrandRatingsDetails;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.*;
import com.hexlindia.drool.product.dto.mapper.AspectTemplateMapper;
import com.hexlindia.drool.product.dto.mapper.ReviewMapper;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.video.business.api.Video;
import com.hexlindia.drool.video.dto.VideoDtoMOngo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    private ProductReviewImpl productReviewSpy;

    @Mock
    private BrandRating brandRatingMock;

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
    private BrandRatingsDetails brandRatingsDetails;

    @Mock
    private UserActivity userActivityMock;

    @BeforeEach
    void setUp() {
        this.productReviewSpy = Mockito.spy(new ProductReviewImpl(brandRatingMock, productReviewRepositoryMock, aspectTemplateMapperMock, reviewMapperMock, videoMock, aspectVotingDetailsMock, userActivityMock));
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

        BrandRatingMetricDto brandRatingMetricDtoTrendy = new BrandRatingMetricDto();
        brandRatingMetricDtoTrendy.setName("Trendy");
        brandRatingMetricDtoTrendy.setRating(4);
        BrandRatingMetricDto brandRatingMetricDtoTrustable = new BrandRatingMetricDto();
        brandRatingMetricDtoTrustable.setName("Trustable");
        brandRatingMetricDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandRatingsDetailsDto brandRatingsDetailsDto = new BrandRatingsDetailsDto(null, null, Arrays.asList(brandRatingMetricDtoTrendy, brandRatingMetricDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDto.setBrandRatingsDetailsDto(brandRatingsDetailsDto);

        when(this.reviewMapperMock.toReviewDoc(reviewDto)).thenReturn(reviewDocMocked);
        ObjectId mockedObjectId = new ObjectId();
        reviewDto.setProductRefDto(new ProductRefDto(mockedObjectId.toHexString(), "MockedProduct", "MOckedCategory"));
        when(this.productReviewRepositoryMock.save(reviewDocMocked, mockedObjectId, aspectVotingDtoList)).thenReturn(reviewDocMocked);
        ObjectId mockedBrandRatingId = new ObjectId();
        when(this.brandRatingsDetails.saveRatings(brandRatingsDetailsDto)).thenReturn(mockedBrandRatingId);
        Mockito.doNothing().when(this.productReviewSpy).saveBrandRatings(any());

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
    @Disabled
    void saveVideReview_passedToVideoBusiness() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewType(ReviewType.video);
        reviewDto.setProductRefDto(new ProductRefDto(ObjectId.get().toHexString(), null, null));

        ReviewDoc reviewDocMocked = new ReviewDoc();
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo();
        ObjectId videoMockedId = ObjectId.get();
        videoDtoMOngo.setId(videoMockedId.toHexString());
        videoDtoMOngo.setType(PostType.review);
        videoDtoMOngo.setActive(true);
        videoDtoMOngo.setTitle("The is a mocked video review");
        videoDtoMOngo.setDescription("this is a mocked video review description");
        videoDtoMOngo.setSourceId("werlkj");
        ProductRefDto productRefDto = new ProductRefDto("p123", "Carolina Herrera 212", "fragrance");
        videoDtoMOngo.setProductRefDtoList(Arrays.asList(productRefDto));
        UserRefDto userRefDto = new UserRefDto("u1123", "User123");
        videoDtoMOngo.setUserRefDto(userRefDto);
        reviewDto.setVideoDtoMOngo(videoDtoMOngo);

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

        BrandRatingMetricDto brandRatingMetricDtoTrendy = new BrandRatingMetricDto();
        brandRatingMetricDtoTrendy.setName("Trendy");
        brandRatingMetricDtoTrendy.setRating(4);
        BrandRatingMetricDto brandRatingMetricDtoTrustable = new BrandRatingMetricDto();
        brandRatingMetricDtoTrustable.setName("Trustable");
        brandRatingMetricDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandRatingsDetailsDto brandRatingsDetailsDto = new BrandRatingsDetailsDto(null, null, Arrays.asList(brandRatingMetricDtoTrendy, brandRatingMetricDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDto.setBrandRatingsDetailsDto(brandRatingsDetailsDto);

        when(this.reviewMapperMock.toReviewDoc(reviewDto)).thenReturn(reviewDocMocked);
        ObjectId mockedProductId = new ObjectId();
        reviewDto.setProductRefDto(new ProductRefDto(mockedProductId.toHexString(), "MockedProduct", "MOckedCategory"));
        reviewDto.setUserRefDto(userRefDto);
        ObjectId mockedBrandRatingId = new ObjectId();
        when(this.brandRatingsDetails.saveRatings(brandRatingsDetailsDto)).thenReturn(mockedBrandRatingId);
        when(this.productReviewRepositoryMock.save(any(), any(), anyList())).thenReturn(reviewDocMocked);
        //FIXME
        //when(this.videoMock.saveOrUpdate(videoDtoMOngo)).thenReturn(videoDtoMOngo);
        Mockito.doNothing().when(this.productReviewSpy).saveBrandRatings(any());
        this.productReviewSpy.save(reviewDto);
        ArgumentCaptor<VideoDtoMOngo> videoDtoArgumentCaptor = ArgumentCaptor.forClass(VideoDtoMOngo.class);

        //FIXME
        //verify(this.videoMock, times(1)).saveOrUpdate(videoDtoArgumentCaptor.capture());
        assertEquals(videoMockedId.toHexString(), videoDtoArgumentCaptor.getValue().getId());
        assertEquals(PostType.review, videoDtoArgumentCaptor.getValue().getType());
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

        BrandRatingMetricDto brandRatingMetricDtoTrendy = new BrandRatingMetricDto();
        brandRatingMetricDtoTrendy.setName("Trendy");
        brandRatingMetricDtoTrendy.setRating(4);
        BrandRatingMetricDto brandRatingMetricDtoTrustable = new BrandRatingMetricDto();
        brandRatingMetricDtoTrustable.setName("Trustable");
        brandRatingMetricDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandRatingsDetailsDto brandRatingsDetailsDto = new BrandRatingsDetailsDto(null, null, Arrays.asList(brandRatingMetricDtoTrendy, brandRatingMetricDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDtoMocked.setBrandRatingsDetailsDto(brandRatingsDetailsDto);

        Mockito.doNothing().when(this.productReviewSpy).saveBrandRatings(any());
        when(this.productReviewRepositoryMock.save(reviewDocMocked, mockedProductId, aspectVotingDtoList)).thenReturn(reviewDocMocked);
        ObjectId mockedBrandRatingId = new ObjectId();
        when(this.brandRatingsDetails.saveRatings(brandRatingsDetailsDto)).thenReturn(mockedBrandRatingId);
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