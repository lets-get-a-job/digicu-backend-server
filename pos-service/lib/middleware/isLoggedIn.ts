import { errorHandling } from '../routing';
import Axios from 'axios';
import { Request, Response, NextFunction } from 'express';

export const isLoggedIn = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const { digicu_token } = req.headers;

    const axiosResponse = await Axios.post(
      process.env.USER_SERVICE_URL + '/authentication/isLoggedIn',
      null,
      {
        headers: {
          digicu_token,
        },
      },
    );

    if (axiosResponse.status === 200) {
      next();
    } else {
      throw Error();
    }
  } catch (error) {
    errorHandling.sendError(res, 401, '인증에 실패했습니다.', error);
  }
};
