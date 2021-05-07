import express from 'express';
import { registerMenu, patchMenu, deleteMenu } from '../lib/query/menu';
import { MenuRegistartion } from '../lib/query/menu/MenuSchema';
import { sendError } from '../lib/routing/error-handling';
const router = express.Router();

/** 메뉴 등록 */
router.post('/', async function (req, res) {
  try {
    await registerMenu(req.body);
    res.send('등록 성공');
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류');
    } else if (error.message === 'no action') {
      sendError(res, 400, '삽입 안됨', '삽입 안됨');
    } else {
      sendError(res, 500, '에러 발생', error);
    }
  }
});

/** 메뉴 수정 */
router.patch('/', async function (req, res) {
  try {
    await patchMenu(req.body);
    res.send('수정 성공');
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류');
    } else if (error.message === 'no action') {
      sendError(res, 400, '수정 안됨', '수정 안됨');
    } else {
      sendError(res, 500, '에러 발생', error);
    }
  }
});

/** 메뉴 삭제 */
router.delete('/', async function (req, res) {
  try {
    await deleteMenu(req.body.menu_id);
    res.send('삭제 성공');
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류');
    } else if (error.message === 'no action') {
      sendError(res, 400, '삭제 안됨', '삭제 안됨');
    } else {
      sendError(res, 500, '에러 발생', error);
    }
  }
});

/** 메뉴 조회 */
router.get('/', async function (req, res) {
  res.send('respond with a resource');
});

export default router;
