# geoplan - Foodly Cost

##  🍎 api 명세

| Method | URI | Description | 개발 완료 | 토큰 필요
| ------ | -- | -- |----------- |
| POST | /users | 회원가입 | ☑️ | ☑️ 
| POST | /users/logIn | 로그인 | ☑️| ☑️ 
| GET | /restaurants/:userIdx | 식당 전체 조회 | ☑️| ☑️ 
| GET | /restaurants/:userIdx?addressKeyword= | 식당 전체 조회(주소키워드검색) | ☑️| ☑️ 
| GET | /restaurants/:userIdx/:restaurantIdx | 식당 개별 조회 |☑️| ☑️ 
| GET | /reviews/:userIdx/restaurants/:restaurantIdx | 식당리뷰 조회 | ☑️| ☑️ 
| GET | /reviews/:userIdx/menus/:restaurantIdx/:menuIdx | 메뉴리뷰 조회 | ☑️| ☑️ 
| POST | /reviews/:userIdx/restaurants/:restaurantIdx | 식당리뷰 등록 | ☑️| ☑️ 
| POST | /reviews/:userIdx/menus/:menuIdx | 메뉴리뷰 등록 | ☑️| ☑️ 
| PATCH | /reviews/:userIdx/restaurants/:reviewIdx | 식당리뷰 수정 |☑️| ☑️ 
| PATCH | /reviews/:userIdx/menus/:reviewIdx | 메뉴리뷰 수정 |☑️| ☑️ 
| PATCH | /reviews/:userIdx/restaurants/:reviewIdx | 식당리뷰 삭제 |☑️| ☑️ 
| PATCH | /reviews/:userIdx/menus/:reviewIdx | 메뉴리뷰 삭제 |☑️| ☑️ 
| GET | /presidents/:userIdx | 식당정보 조회 | ☑️| ☑️ 
