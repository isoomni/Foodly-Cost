package kr.co.geoplan.metro.src.review;


import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.config.BaseResponse;
import kr.co.geoplan.metro.config.BaseResponseStatus;
import kr.co.geoplan.metro.src.review.model.*;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static kr.co.geoplan.metro.config.BaseResponseStatus.*;
import static kr.co.geoplan.metro.utils.ValidationRegex.isRegexReviewContents;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 식당 리뷰 조회 (유저)
     * [GET] /reviews/:userIdx/restaurants/:restaurantIdx
     * @return BaseResponse<GetResReviewPageRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}/restaurants/{restaurantIdx}")
    public BaseResponse<GetResReviewPageRes> getResReview(@PathVariable("userIdx") int userIdx, @PathVariable("restaurantIdx") int id){
        try{
            // jwt에서 idx 추출
            int userIdxByJwt = jwtService.getUserIdx();
            // userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            GetResReviewPageRes getResReviewPageRes = reviewProvider.getResReview(userIdx,id);
            return new BaseResponse<>(getResReviewPageRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 식당 리뷰 등록 (유저)
     * [POST] /reviews/:userIdx/restaurants/:restaurantIdx
     * @return BaseResponse<PostResReviewRes>
     */
    @ResponseBody
    @PostMapping("/{userIdx}/restaurants/{restaurantIdx}")
    public BaseResponse<PostResReviewRes> createResReview(@PathVariable("userIdx") int userIdx, @PathVariable("restaurantIdx") int resId, @RequestBody PostResReviewReq postResReviewReq){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            if(postResReviewReq.getResComment() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_CONTENTS);
            }
            if(postResReviewReq.getResScore() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_STAR);
            }

            //리뷰 내용 정규표현
            if(!isRegexReviewContents(postResReviewReq.getResComment())){  // 글자수 제한
                return new BaseResponse<>(POST_REVIEWS_LONG_CONTENTS);
            }

            PostResReviewRes postResReviewRes = reviewService.createResReview(userIdx, resId, postResReviewReq);
            return new BaseResponse<>(postResReviewRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 식당 리뷰 수정 (유저)
     * [PATCH] /reviews/:userIdx/restaurants/:reviewIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/restaurants/{reviewIdx}") // (PATCH) 127.0.0.1:8080/reviews/:userIdx/:reviewIdx
    public BaseResponse<String> modifyResReview(@PathVariable("userIdx") int userIdx, @PathVariable("reviewIdx") int reviewIdx, @RequestBody ResReview resReview){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(resReview.getResComment() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_CONTENTS);
            }
            if(resReview.getResScore() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_STAR);
            }

            //리뷰 내용 정규표현
            if(!isRegexReviewContents(resReview.getResComment())){  // 글자수 제한
                return new BaseResponse<>(POST_REVIEWS_LONG_CONTENTS);
            }

            PatchResReviewReq patchResReviewReq = new PatchResReviewReq(reviewIdx, resReview.getResComment(), resReview.getResScore());
            reviewService.modifyResReview(patchResReviewReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 식당 리뷰 삭제 (유저)
     * [PATCH] /reviews/:userIdx/restaurants/:reviewIdx/status
     * @return BaseResponse<GetReviewRes>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/restaurants/{reviewIdx}/status")
    public BaseResponse<String> deleteResReview(@PathVariable("userIdx") int userIdx, @PathVariable("reviewIdx") int reviewIdx, @RequestBody ResReviewStatus resReviewStatus){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(resReviewStatus.getStatus() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_STATUS);
            }
            PatchResReviewStatusReq patchResReviewStatusReq = new PatchResReviewStatusReq(reviewIdx, resReviewStatus.getStatus());
            reviewService.deleteResReview(patchResReviewStatusReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 메뉴 리뷰 조회 (유저)
     * [GET] /reviews/:userIdx/menus/:restaurantIdx/:menuIdx
     * @return BaseResponse<GetMenuReviewPageRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}/menus/{restaurantIdx}/{menuIdx}")
    public BaseResponse<GetMenuReviewPageRes> getMenuReview(@PathVariable("userIdx") int userIdx, @PathVariable("restaurantIdx") int restaurantIdx, @PathVariable("menuIdx") int menuIdx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            GetMenuReviewPageRes getMenuReviewPageRes = reviewProvider.getMenuReview(userIdx, restaurantIdx, menuIdx);
                return new BaseResponse<>(getMenuReviewPageRes);
        } catch (BaseException exception){
                return new BaseResponse<>(exception.getStatus());
        }
    }



    /**
     * 메뉴 리뷰 등록 (유저)
     * [POST] /reviews/:userIdx/menus/:menuIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/{userIdx}/menus/{menuIdx}")
    public BaseResponse<PostMenuReviewRes> createMenuReview(@PathVariable("userIdx") int userIdx, @PathVariable("menuIdx") int menuId, @RequestBody PostMenuReviewReq postMenuReviewReq){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            if(postMenuReviewReq.getMenuComment() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_CONTENTS);
            }
            if(postMenuReviewReq.getMenuScore() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_STAR);
            }

            //리뷰 내용 정규표현
            if(!isRegexReviewContents(postMenuReviewReq.getMenuComment())){  // 글자수 제한
                return new BaseResponse<>(POST_REVIEWS_LONG_CONTENTS);
            }

            PostMenuReviewRes postMenuReviewRes = reviewService.createMenuReview(userIdx, menuId, postMenuReviewReq);
            return new BaseResponse<>(postMenuReviewRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 메뉴 리뷰 수정 (유저)
     * [PATCH] /reviews/:userIdx/menus/:reviewIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/menus/{reviewIdx}")
    public BaseResponse<String> modifyResReview(@PathVariable("userIdx") int userIdx, @PathVariable("reviewIdx") int reviewIdx, @RequestBody MenuReview menuReview){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(menuReview.getMenuComment() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_CONTENTS);
            }
            if(menuReview.getMenuScore() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_STAR);
            }

            //리뷰 내용 정규표현
            if(!isRegexReviewContents(menuReview.getMenuComment())){  // 글자수 제한
                return new BaseResponse<>(POST_REVIEWS_LONG_CONTENTS);
            }

            PatchMenuReviewReq patchMenuReviewReq = new PatchMenuReviewReq(reviewIdx, menuReview.getMenuComment(), menuReview.getMenuScore());
            reviewService.modifyMenuReview(patchMenuReviewReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 메뉴 리뷰 삭제 (유저)
     * [PATCH] /reviews/:userIdx/menus/:reviewIdx/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/menus/{reviewIdx}/status")
    public BaseResponse<String> deleteMenuReview(@PathVariable("userIdx") int userIdx, @PathVariable("reviewIdx") int reviewIdx, @RequestBody MenuReviewStatus menuReviewStatus){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(menuReviewStatus.getStatus() == null){
                return new BaseResponse<>(POST_REVIEWS_EMPTY_STATUS);
            }
            PatchMenuReviewStatusReq patchMenuReviewStatusReq = new PatchMenuReviewStatusReq(reviewIdx, menuReviewStatus.getStatus());
            reviewService.deleteMenuReview(patchMenuReviewStatusReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }



}
