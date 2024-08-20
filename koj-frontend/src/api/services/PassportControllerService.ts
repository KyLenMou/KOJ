/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_UserInfoVO_ } from '../models/R_UserInfoVO_';
import type { R_Void_ } from '../models/R_Void_';
import type { UserLoginDTO } from '../models/UserLoginDTO';
import type { UserRegisterDTO } from '../models/UserRegisterDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PassportControllerService {
    /**
     * getRegisterCode
     * @param email email
     * @returns R_Void_ OK
     * @throws ApiError
     */
    public static getRegisterCodeUsingGet(
        email: string,
    ): CancelablePromise<R_Void_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/passport/get-register-code',
            query: {
                'email': email,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * userLogin
     * @param userLoginDto userLoginDTO
     * @returns R_UserInfoVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static userLoginUsingPost(
        userLoginDto: UserLoginDTO,
    ): CancelablePromise<R_UserInfoVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/passport/login',
            body: userLoginDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * userLogout
     * @returns R_Void_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static userLogoutUsingPost(): CancelablePromise<R_Void_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/passport/logout',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * userRegister
     * @param userRegisterDto userRegisterDTO
     * @returns R_UserInfoVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static userRegisterUsingPost(
        userRegisterDto: UserRegisterDTO,
    ): CancelablePromise<R_UserInfoVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/passport/register',
            body: userRegisterDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
