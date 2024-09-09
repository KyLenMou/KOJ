/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_Void_ } from '../models/R_Void_';
import type { SubmissionDTO } from '../models/SubmissionDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SubmitControllerService {
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
