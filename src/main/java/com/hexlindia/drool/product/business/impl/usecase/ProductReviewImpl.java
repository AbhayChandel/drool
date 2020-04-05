package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.AspectVotingDetails;
import com.hexlindia.drool.product.business.api.usecase.BrandRating;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.data.doc.ProductAspectTemplates;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.*;
import com.hexlindia.drool.product.dto.mapper.AspectTemplateMapper;
import com.hexlindia.drool.product.dto.mapper.ReviewMapper;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.VideoDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class ProductReviewImpl implements ProductReview {

    private final BrandRating brandRating;
    private final ProductReviewRepository productReviewRepository;
    private final AspectTemplateMapper aspectTemplateMapper;
    private final ReviewMapper reviewMapper;
    private final Video video;
    private final AspectVotingDetails aspectVotingDetails;
    private final UserActivity userActivity;


    @Override
    public ReviewDialogFormsDto getReviewDialogForms(ObjectId productId, ObjectId brandId) {

        ReviewDialogFormsDto reviewDialogFormsDto = new ReviewDialogFormsDto();
        reviewDialogFormsDto.setProductId(productId.toHexString());
        ProductAspectTemplates productAspectTemplates = this.productReviewRepository.getAspectTemplates(productId);
        reviewDialogFormsDto.setAspectTemplateDtoList(aspectTemplateMapper.toDtoList(productAspectTemplates.getAspectTemplates()));
        reviewDialogFormsDto.setBrandId(brandId.toHexString());
        reviewDialogFormsDto.setBrandRatingMetrics(brandRating.getRatingMetrics(brandId.toHexString()));
        return reviewDialogFormsDto;
    }

    @Override
    public ReviewDto save(ReviewDto reviewDto) {

        ReviewDoc reviewDoc = reviewMapper.toReviewDoc(reviewDto);
        ObjectId productId = new ObjectId(reviewDto.getProductRefDto().getId());

        reviewDoc = productReviewRepository.save(reviewDoc, productId, reviewDto.getAspectVotingDtoList());
        reviewDto.setId(reviewDoc.getId().toHexString());

        if (ReviewType.text.equals(reviewDto.getReviewType())) {
            userActivity.addTextReview(reviewDoc);
        }

        ObjectId videoId = saveVideoReview(reviewDto, reviewDoc);
        saveAspectVotingDetails(reviewDto);
        saveBrandRatings(reviewDto);

        if (ReviewType.video.equals(reviewDto.getReviewType()) && videoId != null) {
            productReviewRepository.setVideoId(productId, reviewDoc.getId(), videoId);
        }

        return reviewDto;
    }

    private ObjectId saveVideoReview(ReviewDto reviewDto, ReviewDoc reviewDoc) {
        if (ReviewType.video.equals(reviewDto.getReviewType())) {
            VideoDto videoDto = reviewDto.getVideoDto();
            videoDto.setReviewId(reviewDoc.getId().toHexString());
            videoDto.setProductRefDtoList(Arrays.asList(reviewDto.getProductRefDto()));
            videoDto.setUserRefDto(reviewDto.getUserRefDto());
            videoDto = video.save(videoDto);
            reviewDto.setVideoDto(videoDto);
            return new ObjectId(videoDto.getId());
        }
        return null;
    }

    private void updateReviewIdInVideoDoc(ObjectId videoId, ObjectId reviewId) {
        if (videoId != null) {
            video.updateReviewId(videoId, reviewId);
        }
    }

    private void saveAspectVotingDetails(ReviewDto reviewDto) {
        if (!reviewDto.getAspectVotingDtoList().isEmpty()) {
            this.aspectVotingDetails.save(new AspectVotingDetailsDto(reviewDto.getAspectVotingDtoList(), reviewDto.getId(), reviewDto.getProductRefDto(), reviewDto.getUserRefDto()));
        }
    }

    private void saveBrandRatings(ReviewDto reviewDto) {
        BrandRatingsDetailsDto brandRatingsDetailsDto = reviewDto.getBrandRatingsDetailsDto();
        brandRatingsDetailsDto.setReviewId(reviewDto.getId());
        brandRatingsDetailsDto.setUserRefDto(reviewDto.getUserRefDto());
        boolean userRated = false;
        if (brandRatingsDetailsDto.getBrandRatingMetricDtoList() != null) {
            for (BrandRatingMetricDto brandRatingMetricDto : brandRatingsDetailsDto.getBrandRatingMetricDtoList()) {
                if (brandRatingMetricDto.getRating() > 0) {
                    userRated = true;
                    break;
                }
            }
        }
        if (userRated) {
            ObjectId id = brandRating.saveBrandRatings(brandRatingsDetailsDto);
            reviewDto.getBrandRatingsDetailsDto().setId(id.toHexString());
        }
    }


}
