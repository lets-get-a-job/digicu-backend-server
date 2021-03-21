# User Service

## Commitlint 사용

---

현재 프로젝트에서는 Commitlint를 적용해보려고 합니다.

한번 사용해보고 사용해볼만하면 말씀드리겠습니다.

### 요구사항

```bash
$ npm install -g @commitlint/cli @commitlint/config-conventional
```

### 명령어

```bash
npm run commit
```

## DB 인증 관련

---

MYSQL USER 및 PASSWORD는 보안의 이유로 dotenv 모듈을 이용합니다.

.env 파일을 다음과 같이 생성해주세요.

```
DB_HOST = localhost
DB_NAME = your_db_name
DB_USER = your_db_user_id
DB_PASS = your_db_password
```

## 포트 설정

---

포트도 .env 파일에 넣습니다.

```
PORT = 3000
```

## 개발 모드, 배포 모드

package.json에 다음과 같은 커맨드를 등록해놨습니다.

- `npm run dev`: 개발 모드
- `npm run prod`: 배포 모드

코드 상으로는 `process.env.NODE_ENV`로 구분합니다.

```javascript
if (process.env.NODE_ENV === 'development') {
  // ... development 코드
}

if (process.env.NODE_ENV === 'production') {
  // ... production 코드
}
```

## TODO

---

- [ ] user table 생성
- [ ] mysql2 모듈을 기반 라이브러리 작성
- [ ] 회원가입
  - 이메일 조회 (중복 검사)
  - 이메일 인증
- [ ] 유저 조회
- [ ] 패스워드 찾기
- [ ] 회원탈퇴
- [ ] 비밀번호 변경
  - 이메일 인증

> 이메일 인증은 화이트 도메인 문제로 우선 네이버 SMTP를 사용하도록 하겠습니다.
