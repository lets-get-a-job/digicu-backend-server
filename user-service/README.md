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
API_DOCS_PORT = 3001
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

## Nodemailer

이메일 인증 관련하여 노드메일러를 사용하고 있습니다.

서버가 화이트 도메인 인증이 되어 있지 않기 때문에, SMTP를 사용해서 메일을 보냅니다.

네이버 SMTP 계정을 사용하시면 됩니다.

.env파일에 STMP 정보를 입력해주세요.

SMTP_USER에 맞는 SMTP_FROM을 작성해주세요.

```
SMTP_USER = your_user_id
SMTP_PASS = your_mail_password
SMTP_HOST = your_mail_host
SMTP_PORT = your_smtp_port
SMTP_SSL = true or false
SMTP_ORIGIN = 어디서 요청했는지 (예] http://localhost:3000)
SMTP_FROM = your_user_id@domain
```

## JWT_SECRET

알려진 JWT_SECRET 값이 필요합니다.

.env 파일에 추가해주세요.

```
JWT_SECRET = ...
```

## TODO

---

- [v] user table 생성
- [v] mysql2 모듈을 기반 라이브러리 작성
- [v] 회원가입
  - 이메일 조회 (중복 검사)
  - 이메일 인증
- [v] 유저 조회
- [v] 비밀번호 변경
- [v] swagger 기반으로 API 작성
- [v] jwt 토큰 기반 로그인 서비스
- [ ] 회원탈퇴
- [ ] 유저 프로필 변경

> 이메일 인증은 화이트 도메인 문제로 우선 네이버 SMTP를 사용하도록 하겠습니다.
