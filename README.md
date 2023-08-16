# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴십 - 선발 과제

## 🙇🏻‍♂️ 김대희

## 애플리케이션의 실행 방법
- <b>응답값</b>
  - code : 0000 , message : OK 가 오면 성공이다.
- <b>회원</b>
  - POST http://3.35.107.49:8081/auth/sign (회원가입)
    - Json 형식으로 email 과 password 를 입력하면 된다.
    - 단 email은 @ 이 포함되어야 하며, password 는 공백제외 8자 이상이어야한다
  - POST http://3.35.107.49:8081/auth/login (로그인)
    - Json 형식으로 email 과 password 를 입력하면 된다.
    - 단 email은 @ 이 포함되어야 하며, password 는 공백제외 8자 이상이어야한다
    - 유저 인증 성공 시, jwt(accessToken, refreshToken) 이 발급된다.
- <b>게시글</b>
  - 모든 게시글은 Header 에 Token 값이 들어가야한다. Token 값으로 요청 유저의 정보를 확인하며 올바르지 않은 토큰일시 401 Http Status 를 반환한다.
  - POST http://3.35.107.49:8081/board (게시글 작성)
    - Json 형식으로 title 과 content 를 입력하면 된다.
    - 요청의 유저로 writer_id 이 설정된다.
  - GET http://3.35.107.49:8081/board/all?page=0&size=2 (게시글 리스트 조회)
    - Request Parm 형식으로 page 와 size 의 값을 주면 된다.
    - size 의 크기만큼 paging 처리한다.
  - GET http://3.35.107.49:8081/board/{boardId} (게시글 리스트 조회)
    - PathVariable 형식으로 boardId 값을 주면 된다. ex) board/1
  - PUT http://3.35.107.49:8081/board/{boardId} (게시글 수정)
    - PathVariable 형식으로 boardId 값을 주면 된다. ex) board/1
    - writer ID 와 요청 토큰의 userId 가 다를 경우 권한 에러가 발생한다.
  - DELETE http://3.35.107.49:8081/board/{boardId} (게시글 삭제)
    - PathVariable 형식으로 boardId 값을 주면 된다. ex) board/1
    - writer ID 와 요청 토큰의 userId 가 다를 경우 권한 에러가 발생한다.
    
<br>

## 🔓 ERD
<img width="508" alt="스크린샷 2023-08-16 오후 3 22 39" src="https://github.com/eet43/wanted-pre-onboarding-backend/assets/59008469/e2b63153-cf08-4605-9c1b-0847e5051ecd">


<b>두 엔티티간 일대다 연관관계를 맺을 수 있지만, 두 엔티티의 라이프사이클이 다르기 때문에 연관관계로 묶기보다는 pk 값으로 repo 조회 할 수 있게 설계했습니다. 필요시 Join 을 통해 값을 가져오면 되고, 라이프 사이클과 비즈니스 로직이 비슷한 ex) 게시글, 댓글 경우가 아니라면 연관관계를 설정하지 않아 시스템 복잡도를 낮추어 설계하는게 좋은 설계라고 인지하고 있기 때문입니다.
<br>

## 🌹 데모 영상 링크

<br>

## 🔥 구현 방법
- <b>EC2, RDS<b>
  - 프리티어의 gmail 의 더이상 생성하기 힘들어, 기존에 있는 ec2 서버와 rds 를 사용하였습니다.
  - 현재 배포한 앱이 있기 때문에 port 는 8081 로 설정하고, 보안그룹의 인바운드 규칙에 8081 포트를 추가하였습니다.
- <b>Java 17<b>
  - stream toList 를 편하게 생성하였습니다.
  - record 타입을 Dto 에 사용하였습니다.
- <b>핵사고날 아키텍처</b>  
  - 역할과 책임을 분리하고 객체지향성 설계에 초점을 맞추었습니다.
- <b>Security 도입</b>
  - 토큰 기반 인증이 필요하기 때문에, 토큰마다 유효한 토큰인지, 조작되지 않았는지 매요청마다 확인하고, 사용자의 정보를 담은 세션을 저장해 각 필터를 통해 인증을 수행하는 Security 를 도입해 토큰 기반 인증 설계를 진행하였습니다.
- <b>Spring Data JPA</b>  
  - 객체 중심의 ORM 쿼리 활용하고 간편하게 쿼리를 작성하기 위해 Data JPA 를 사용하였습니다.
- <b>MySQL 8.0</b>  
  - 시스템 복잡도를 줄이기 위해 라이프사이클이 같은 엔티티만 연관관계로 설정하였습니다.
- <b>테스트</b>
  - 단순 호출이 아니고, 비즈니스 로직이 포함된 부분은 테스트 코드를 작성했습니다.
  - 유닛테스트 코드부터, 게시글 생성, 수정, 조회, 삭제까지 한 사이클로 통합테스트 또한 진행하였습니다.
  - 필요한 객체는 Mocking 을 통해 생성하였습니다.
- <b>API 명세서</b>
  - 명세서를 위해 간편한 Swagger 를 설정하였습니다.


<br>

## API 명세서

http://3.35.107.49:8081/swagger-ui/index.html#/


