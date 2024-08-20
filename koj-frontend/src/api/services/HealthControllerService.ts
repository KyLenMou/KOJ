/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_string_ } from '../models/R_string_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class HealthControllerService {
    /**
     * getHealth
     * @returns R_string_ OK
     * @throws ApiError
     */
    public static getHealthUsingGet(): CancelablePromise<R_string_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/health',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
