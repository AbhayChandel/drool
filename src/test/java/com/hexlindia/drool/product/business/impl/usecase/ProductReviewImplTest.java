package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.data.doc.TextReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.ReviewDto;
import com.hexlindia.drool.product.dto.mapper.TextReviewMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductReviewImplTest {

    private ProductReview productReviewSpy;

    @Mock
    private ProductReviewRepository productReviewRepositoryMock;

    @Mock
    private TextReviewMapper textReviewMapperMock;

    @BeforeEach
    void setUp() {
        this.productReviewSpy = Mockito.spy(new ProductReviewImpl(productReviewRepositoryMock, textReviewMapperMock));
    }

    @Test
    void saveReviewText_passedToRepositoryLayer() {
        ReviewDto reviewDto = new ReviewDto();

        TextReviewDoc textReviewDocMocked = new TextReviewDoc();
        textReviewDocMocked.setReviewType("text");
        textReviewDocMocked.setRecommendation("1");
        textReviewDocMocked.setDetailedReview("THis is a details text review");
        textReviewDocMocked.setReviewSummary("This is text review summary");

        when(this.textReviewMapperMock.toReviewDoc(reviewDto)).thenReturn(textReviewDocMocked);
        ObjectId mockedObjectId = new ObjectId();
        reviewDto.setProductRefDto(new ProductRefDto(mockedObjectId.toHexString(), "MockedProduct", "MOckedCategory"));
        when(this.productReviewRepositoryMock.save(textReviewDocMocked, mockedObjectId)).thenReturn(textReviewDocMocked);
        this.productReviewSpy.save(reviewDto);
        ArgumentCaptor<TextReviewDoc> reviewDocArgumentCaptor = ArgumentCaptor.forClass(TextReviewDoc.class);
        ArgumentCaptor<ObjectId> productIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);

        verify(this.productReviewRepositoryMock, times(1)).save(reviewDocArgumentCaptor.capture(), productIdArgumentCaptor.capture());
        assertEquals("text", reviewDocArgumentCaptor.getValue().getReviewType());
        assertEquals("1", reviewDocArgumentCaptor.getValue().getRecommendation());
        assertEquals("THis is a details text review", reviewDocArgumentCaptor.getValue().getDetailedReview());
        assertEquals("This is text review summary", reviewDocArgumentCaptor.getValue().getReviewSummary());
        assertEquals(mockedObjectId, productIdArgumentCaptor.getValue());
    }
}