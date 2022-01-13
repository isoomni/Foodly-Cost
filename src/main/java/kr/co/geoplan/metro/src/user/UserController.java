package kr.co.geoplan.metro.src.user;

import kr.co.geoplan.metro.config.BaseException;
import kr.co.geoplan.metro.config.BaseResponse;
import kr.co.geoplan.metro.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import kr.co.geoplan.metro.src.user.model.*;

import java.util.List;

import static kr.co.geoplan.metro.config.BaseResponseStatus.*;
import static kr.co.geoplan.metro.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    /**
     * 회원 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{id}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("id") int id) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(id != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            //같다면 유저네임 변경
            GetUserRes getUsersRes = userProvider.getUser(id);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {

        if(postUserReq.getEmail_address() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(postUserReq.getUserName() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_USERNAME);
        }
        if(postUserReq.getPassword() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        if(postUserReq.getPassword() == postUserReq.getEmail_address()){ // 아이디와 비밀번호가 같습니다.
            return new BaseResponse<>(POST_USERS_PASSWORD_SAME_WITH_EMAIL);
        }

        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail_address())){  // 정규표현식과 다른 형식으로 받으면 invalid
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        //비밀번호 정규표현
        if (!isRegexPassword(postUserReq.getPassword())){  // 특수문자 / 문자 / 숫자 포함 형태의 8~20자리 이내의 암호 정규식
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }


        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            if(postLoginReq.getEmail_address() == null){ //이메일을 입력하지 않음
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if(postLoginReq.getPassword() == null){    //비밀번호를 입력하지 않음
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if(postLoginReq.getPassword() == postLoginReq.getEmail_address()){ // 아이디와 비밀번호가 같습니다.
                return new BaseResponse<>(POST_USERS_PASSWORD_SAME_WITH_EMAIL);
            }

            //이메일 정규표현
            if(!isRegexEmail(postLoginReq.getEmail_address())){  // 정규표현식과 다른 형식으로 받으면 invalid
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            //비밀번호 정규표현
            if (!isRegexPassword(postLoginReq.getPassword())){  // 특수문자 / 문자 / 숫자 포함 형태의 8~20자리 이내의 암호 정규식
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
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
