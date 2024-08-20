/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_UserHomeInfoVO_ } from '../models/R_UserHomeInfoVO_';
import type { R_UserInfoVO_ } from '../models/R_UserInfoVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UserInfoControllerService {
    /**
     * getCurrentUserInfo
     * @returns R_UserInfoVO_ OK
     * @throws ApiError
     */
    public static getCurrentUserInfoUsingGet(): CancelablePromise<R_UserInfoVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/user/current-user',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getUserHomeInfo
     * @param userId userId
     * @param username username
     * @returns R_UserHomeInfoVO_ OK
     * @throws ApiError
     */
    public static getUserHomeInfoUsingGet(
        userId?: string,
        username?: string,
    ): CancelablePromise<R_UserHomeInfoVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/user/get-user-home-info',
            query: {
                'userId': userId,
                'username': username,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
