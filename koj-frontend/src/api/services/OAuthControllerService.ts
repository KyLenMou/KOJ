/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_UserInfoVO_ } from '../models/R_UserInfoVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class OAuthControllerService {
    /**
     * redirectByGithub
     * @param code code
     * @returns R_UserInfoVO_ OK
     * @throws ApiError
     */
    public static redirectByGithubUsingGet(
        code: string,
    ): CancelablePromise<R_UserInfoVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/oauth/github',
            query: {
                'code': code,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
