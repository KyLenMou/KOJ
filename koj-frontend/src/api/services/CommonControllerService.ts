/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_int_ } from '../models/R_int_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class CommonControllerService {
    /**
     * getQueueSize
     * @returns R_int_ OK
     * @throws ApiError
     */
    public static getQueueSizeUsingGet(): CancelablePromise<R_int_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/queue-size',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
