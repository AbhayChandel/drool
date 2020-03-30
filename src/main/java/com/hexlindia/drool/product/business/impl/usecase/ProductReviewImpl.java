package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.AspectVotingDetails;
import com.hexlindia.drool.product.business.api.usecase.BrandEvaluation;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
import com.hexlindia.drool.product.dto.BrandCriteriaRatingsDetailsDto;
import com.hexlindia.drool.product.dto.BrandCriterionRatingDto;
import com.hexlindia.drool.product.dto.ReviewDto;
import com.hexlindia.drool.product.dto.mapper.ReviewMapper;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.VideoDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class ProductReviewImpl implements ProductReview {

    private final ProductReviewRepository productReviewRepository;
    private final ReviewMapper reviewMapper;
    private final Video video;
    private final AspectVotingDetails aspectVotingDetails;
    private final BrandEvaluation brandEvaluation;

    @Override
    public ReviewDto save(ReviewDto reviewDto) {

        ReviewDoc reviewDoc = reviewMapper.toReviewDoc(reviewDto);
        saveVideoReview(reviewDto, reviewDoc);
        reviewDoc = productReviewRepository.save(reviewDoc, new ObjectId(reviewDto.getProductRefDto().getId()), reviewDto.getAspectVotingDtoList());
        updateReviewIdInVideoDoc(reviewDoc.getVideoId(), reviewDoc.getId());
        reviewDto.setId(reviewDoc.getId().toHexString());

        saveAspectVotingDetails(reviewDto);
        saveBrandEvaluationCriteriaRatings(reviewDto);

        return reviewDto;
    }

    private void saveVideoReview(ReviewDto reviewDto, ReviewDoc reviewDoc) {
        if (ReviewType.video.equals(reviewDto.getReviewType())) {
            VideoDto videoDto = reviewDto.getVideoDto();
            videoDto.setProductRefDtoList(Arrays.asList(reviewDto.getProductRefDto()));
            videoDto.setUserRefDto(reviewDto.getUserRefDto());
            videoDto = video.save(videoDto);
            reviewDto.setVideoDto(videoDto);
            reviewDoc.setVideoId(new ObjectId(videoDto.getId()));
        }
    }

    private void updateReviewIdInVideoDoc(ObjectId videoId, ObjectId reviewId) {
        if (videoId != null) {
            video.updateReviewId(videoId, reviewId);
        }
    }

    private void saveAspectVotingDetails(ReviewDto reviewDto) {
        if (reviewDto.getAspectVotingDtoList().size() > 0) {
            this.aspectVotingDetails.save(new AspectVotingDetailsDto(reviewDto.getAspectVotingDtoList(), reviewDto.getProductRefDto(), reviewDto.getUserRefDto()));
        }
    }

    private void saveBrandEvaluationCriteriaRatings(ReviewDto reviewDto) {
        BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto = reviewDto.getBrandCriteriaRatingsDetailsDto();
        brandCriteriaRatingsDetailsDto.setUserRefDto(reviewDto.getUserRefDto());
        boolean userRated = false;
        for (BrandCriterionRatingDto brandCriterionRatingDto : brandCriteriaRatingsDetailsDto.getBrandCriterionRatingDtoList()) {
            if (brandCriterionRatingDto.getRating() > 0) {
                userRated = true;
                break;
            }
        }
        if (userRated) {
            ObjectId id = brandEvaluation.saveCriteriaRatings(brandCriteriaRatingsDetailsDto);
            reviewDto.getBrandCriteriaRatingsDetailsDto().setId(id.toHexString());
        }
    }


}
