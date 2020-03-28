package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.AspectVotingDetails;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
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

    @Override
    public ReviewDto save(ReviewDto reviewDto) {
        ReviewDoc reviewDoc = reviewMapper.toReviewDoc(reviewDto);
        if (ReviewType.video.equals(reviewDto.getReviewType())) {
            VideoDto videoDto = saveVideo(reviewDto);
            reviewDto.setVideoDto(videoDto);
            reviewDoc.setVideoId(new ObjectId(videoDto.getId()));
        }

        reviewDoc = productReviewRepository.save(reviewDoc, new ObjectId(reviewDto.getProductRefDto().getId()), reviewDto.getAspectVotingDtoList());
        updateReviewIdInVideoDoc(reviewDoc.getVideoId(), reviewDoc.getId());
        if (reviewDto.getAspectVotingDtoList().size() > 0) {
            saveAspectVotingDetails(new AspectVotingDetailsDto(reviewDto.getAspectVotingDtoList(), reviewDto.getProductRefDto(), reviewDto.getUserRefDto()));
        }
        reviewDto.setId(reviewDoc.getId().toHexString());
        return reviewDto;
    }

    private VideoDto saveVideo(ReviewDto reviewDto) {
        VideoDto videoDto = reviewDto.getVideoDto();
        videoDto.setProductRefDtoList(Arrays.asList(reviewDto.getProductRefDto()));
        videoDto.setUserRefDto(reviewDto.getUserRefDto());
        return video.save(videoDto);
    }

    private void saveAspectVotingDetails(AspectVotingDetailsDto aspectVotingDetailsDto) {
        this.aspectVotingDetails.save(aspectVotingDetailsDto);
    }

    private void updateReviewIdInVideoDoc(ObjectId videoId, ObjectId reviewId) {
        video.updateReviewId(videoId, reviewId);
    }
}
