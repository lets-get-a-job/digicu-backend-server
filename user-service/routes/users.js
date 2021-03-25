const express = require('express');
const router = express.Router();

const { registraton, search, authentication } = require('../lib/query/users');
const { errorHandling } = require('../lib/routing');

/** 이메일로 회사 조회 */
router.get('/company/:email', async (req, res) => {
  try {
    const user = await search.findCompanyByEmail(req.params.email);
    res.json(user);
  } catch (error) {
    errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
  }
});

/** 사업자등록번호로 회사 조회 */
router.get('/company/:companyNumber', async (req, res) => {
  try {
    const user = await search.findCompanyByEmail(req.params.email);
    res.json(user);
  } catch (error) {
    errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
  }
});

/** 회사 등록 */
router.post('/company/', async (req, res) => {
  try {
    const { regInfo, companyInfo } = req.body;
    const isRegisterd = await registraton.registerCompany(regInfo, companyInfo);
    if (isRegisterd) {
      res.sendStatus(200);
    } else {
      res.sendStatus(400);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
  }
});

/** 가입 인증이 되었는지 확인 */
router.get('/:email/checked', async (req, res) => {
  try {
    const isChecked = await authentication.isEmailChecked(
      'formail0001@naver.com',
    );
    res.json({
      isChecked,
    });
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

// router.get('/change_password_test', async (req, res) => {
//   res.render('change-password-test', {
//     email: 'formail0001@naver.com',
//   });
// });

// router.get('/register_test', async (req, res) => {
//   try {
//     const isRegisterd = await registraton.registerCompany(
//       {
//         email: 'formail0001@naver.com',
//         plainPassword: 'thisispassword1234',
//         letterOK: true,
//       },
//       {
//         companyNumber: '1248100998',
//         companyName: '삼성전자 주식회사',
//         companyPhone: '0222550114',
//         companyAddress: '수원시 영통구 삼성로 129',
//         companyOwner: '이재용',
//         companyHomepage: 'https://samsung.com/',
//       },
//     );
//     if (isRegisterd) {
//       res.send('유저 등록 성공');
//     } else {
//       res.status(400);
//       res.send('유저 등록 실패');
//     }
//   } catch (error) {
//     res.status(400);
//     if (process.env.NODE_ENV === 'development') {
//       res.send(`error message: ${error}`);
//     } else {
//       res.send('유저 등록 실패');
//     }
//   }
// });

module.exports = router;
