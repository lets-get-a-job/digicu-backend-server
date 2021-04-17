# Change Log

## 2021-04-18

- company logo 추가할 수 있게 변경
- [v] 업체 프로필 변경
  PATCH /users/company/{companyNumber}
- [v] 업체 검색 API
  GET /users/company
  ?include=
  &count=
  &page=
- [v] 회원탈퇴 (소셜, 업체)
  DELETE /users/company/{companyNumber}
  DELETE /users/social/{social_id}

## 2021-04-14

- 소셜 로그인 백엔드 API 추가
- 업데이트를 하는 경우 기존 PUT -> PATCH 메서드로 변경
- 기존 로그인 문제 해결

## 2021-04-09

- SMTP 메일 부분 수정
- 인증된 회원만 로그인 가능하게

## 2021-04-07

- JWT 기반 로그인 API 구현

## 2021-03-26

- API 완성
- jwt 기반 로그인 서비스 계획 수립

## 2021-03-25

- Swagger API 작성

## 2021-03-24

- 변경된 DB에 맞게 리팩토링

## 2021-03-23

- [v] 회원가입
  - 이메일 조회 (중복 검사)
  - 이메일 인증
- [v] 유저 조회
- [v] 비밀번호 변경

## 2021-03-22

---

DB 연동까지만 작업했습니다.

`GET /users` 요청 시 SQL 호출
