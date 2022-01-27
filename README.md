# geoplan - Foodly Cost

## :octocat: api 명세

| Method | URI | Description | 개발 완료 | 토큰 필요|
| ------ | -- | -- |----------- |---------- |
| POST | /users | [회원가입](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/user) | ☑️ |  | 
| POST | /users/logIn | [로그인](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/user) | ☑️|  |  |
| GET | /restaurants/:userIdx | [식당 전체 조회](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/restaurant) | ☑️| ☑️ |  |
| GET | /restaurants/:userIdx?addressKeyword= | [식당 전체 조회(주소키워드검색)](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/restaurant) | ☑️| ☑️ |  |
| GET | /restaurants/:userIdx/:restaurantIdx | [식당 개별 조회](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/restaurant) |☑️| ☑️ |  |
| GET | /reviews/:userIdx/restaurants/:restaurantIdx | [식당리뷰 조회](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) | ☑️| ☑️ |  |
| GET | /reviews/:userIdx/menus/:restaurantIdx/:menuIdx | [메뉴리뷰 조회](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) | ☑️| ☑️ |  |
| POST | /reviews/:userIdx/restaurants/:restaurantIdx | [식당리뷰 등록](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) | ☑️| ☑️ |  |
| POST | /reviews/:userIdx/menus/:menuIdx | [메뉴리뷰 등록](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) | ☑️| ☑️ |  |
| PATCH | /reviews/:userIdx/restaurants/:reviewIdx | [식당리뷰 수정](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) |☑️| ☑️ |  |
| PATCH | /reviews/:userIdx/menus/:reviewIdx | [메뉴리뷰 수정](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) |☑️| ☑️ |  |
| PATCH | /reviews/:userIdx/restaurants/:reviewIdx | [식당리뷰 삭제](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) |☑️| ☑️ |  |
| PATCH | /reviews/:userIdx/menus/:reviewIdx | [메뉴리뷰 삭제](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/review) |☑️| ☑️ |  |
| GET | /presidents/:userIdx | [식당정보 조회](https://github.com/isoomni/geoplan/tree/master/src/src/main/java/kr/co/geoplan/metro/src/president) | ☑️| ☑️ |  |
