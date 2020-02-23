package com.hexlindia.drool.video.services.api.rest;

import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/video")
public interface VideoRestService {

    @PostMapping("/insert")
    ResponseEntity<VideoDto> insert(@RequestBody VideoDto videoDto);

    @PutMapping(value = "/likes/increment")
    ResponseEntity<String> incrementVideoLikes(@RequestBody VideoLikeUnlikeDto videoLikeUnlikeDto);

    @PutMapping(value = "/likes/decrement")
    ResponseEntity<String> decrementVideoLikes(@RequestBody VideoLikeUnlikeDto videoLikeUnlikeDto);

    @PutMapping(value = "/insert/comment")
    ResponseEntity<VideoCommentDto> insertComment(@RequestBody VideoCommentDto videoCommentDto);

    @PutMapping(value = "/delete/comment")
    ResponseEntity<Boolean> deleteComment(@RequestBody VideoCommentDto videoCommentDto);

    @PutMapping(value = "/comment/likes/increment")
    ResponseEntity<String> saveCommentLike(@RequestBody VideoCommentDto videoCommentDto);

    @PutMapping(value = "/comment/likes/decrement")
    ResponseEntity<String> deleteCommentLike(@RequestBody VideoCommentDto videoCommentDto);


}
