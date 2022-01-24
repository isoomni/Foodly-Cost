package kr.co.geoplan.metro.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_PHONENUMBER(false, 2014, "전화번호를 입력해주세요"),
    POST_USERS_EMPTY_PASSWORD(false, 2012, "비밀번호를 입력해주세요"),
    POST_USERS_EMPTY_USERNAME(false, 2013, "사용자명을 입력해주세요"),
    POST_USERS_EMPTY_EMAIL(false, 2011, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."), // 형식적 validation
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_PASSWORD_SAME_WITH_EMAIL(false, 2018, "이메일과 비밀번호가 같습니다."),
    POST_USERS_INVALID_PASSWORD(false,2019,"비밀번호 형식을 확인해주세요."),
    POST_USERS_INVALID_PHONENUMBER(false,2020,"전화번호 형식을 확인해주세요."),
    POST_USERS_WITHDRAWAL(false, 2021, "탈퇴한 유저입니다."),
    POST_USERS_EMPTY_TYPE(false, 2022,"유저 타입을 입력하세요"),

    // [POST] /reviews
    POST_REVIEWS_EMPTY_CONTENTS(false, 2040, "리뷰를 입력해 주세요."),
    POST_REVIEWS_EMPTY_STAR(false, 2041, "별점을 입력해 주세요."),
    POST_REVIEWS_LONG_CONTENTS(false,2042,"리뷰가 500자를 초과하였습니다."),
    POST_REVIEWS_EMPTY_STATUS(false, 2043, "리뷰의 status가 없습니다."),

    // [GET] /presidents
    POST_PRESIDENTS_USERIDX_IS_NOT_P(false,2050,"사장님 계정이 아닌 계정으로 접속하셨습니다"),

    // [GET] /restaurants
    POST_ADDRESS_PARAM_CONTAINS_SPACE(false,2060,"띄어쓰기를 제거하고 명사 형태로 검색하세요(예: 강남, 삼성)"),

// code 숫자 중복되지 않도록 하는 class 만들어 보고 싶음.

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    // REVIEW
    REVIEW_MODIFY_FAIL(false,3020,"리뷰 수정 실패"),
    REVIEW_DELETE_FAIL(false,3020,"리뷰 삭제 실패"),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
