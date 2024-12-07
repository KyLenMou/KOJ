/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DebugDTO } from '../models/DebugDTO';
import type { R_DebugVO_ } from '../models/R_DebugVO_';
import type { R_string_ } from '../models/R_string_';
import type { R_Void_ } from '../models/R_Void_';
import type { SubmissionDTO } from '../models/SubmissionDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SubmitControllerService {
    /**
     * getDebugResult
     * @param debugId debugId
     * @returns R_DebugVO_ OK
     * @throws ApiError
     */
    public static getDebugResultUsingGet(
        debugId: string,
    ): CancelablePromise<R_DebugVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/debug',
            query: {
                'debugId': debugId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * debug
     * @param debugDto debugDTO
     * @returns R_string_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static debugUsingPost(
        debugDto: DebugDTO,
    ): CancelablePromise<R_string_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/debug',
            body: debugDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * submit
     * @param submission submission
     * @returns R_Void_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static submitUsingPost(
        submission: SubmissionDTO,
    ): CancelablePromise<R_Void_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/submit',
            body: submission,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
