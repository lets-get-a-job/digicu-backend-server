## 스크립트

---

```
# 개발용
npm run dev

# 배포용
npm run prod
```

기본 포트는 7777입니다. (process.env.PORT로 정의 가능)

## 이벤트

---

### 그룹 참여 ('join')

- QR -> Server
- POS -> Server

```
socket.emit('join', {
  company_id: ''
})
```

정상적으로 join이 되야 통신이 이루어집니다.

### Sender ('enqueue')

---

- QR -> Server

```
socket.emit('enqueue', {
  coupon_ids: string[]
})
```

### Receiver ('dequeue')

---

- Server -> POS

```
socket.emit('dequeue', {
  coupon_ids: string[]
})
```

## 참고

클라이언트 구현시 client-test를 참고하시면 됩니다.
