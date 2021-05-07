import { errorHandling } from '../routing';
import Axios from 'axios';

export const isLoggedIn = async (req, res, next) => {
  try {
    const { token } = req.body;

    const axiosResponse = await Axios.post(
      process.env.USER_SERVICE_URL + '/authentication/isLoggedIn',
      {
        token,
      },
    );

    if (axiosResponse.status === 200) {
      next();
    } else {
      throw Error();
    }
  } catch (error) {
    errorHandling.sendError(
      res,
      401,
      '인증에 실패했습니다.',
      '인증에 실패했습니다.',
    );
  }
};
