/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_Page_SubmissionListVO_ } from '../models/R_Page_SubmissionListVO_';
import type { R_SubmissionDetailVO_ } from '../models/R_SubmissionDetailVO_';
import type { R_SubmissionVerdictVO_ } from '../models/R_SubmissionVerdictVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SubmissionControllerService {
    /**
     * listSubmissionByPage
     * @param current current
     * @param size size
     * @param language language
     * @param problemDisplayId problemDisplayId
     * @param problemId problemId
     * @param userId userId
     * @param username username
     * @returns R_Page_SubmissionListVO_ OK
     * @throws ApiError
     */
    public static listSubmissionByPageUsingGet(
        current: number,
        size: number,
        language?: string,
        problemDisplayId?: string,
        problemId?: number,
        userId?: string,
        username?: string,
    ): CancelablePromise<R_Page_SubmissionListVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/submission',
            query: {
                'current': current,
                'language': language,
                'problemDisplayId': problemDisplayId,
                'problemId': problemId,
                'size': size,
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
    /**
     * getSubmissionDetail
     * @param submissionId submissionId
     * @returns R_SubmissionDetailVO_ OK
     * @throws ApiError
     */
    public static getSubmissionDetailUsingGet(
        submissionId: number,
    ): CancelablePromise<R_SubmissionDetailVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/submission/detail',
            query: {
                'submissionId': submissionId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getSubmissionVerdict
     * @param submissionId submissionId
     * @returns R_SubmissionVerdictVO_ OK
     * @throws ApiError
     */
    public static getSubmissionVerdictUsingGet(
        submissionId: number,
    ): CancelablePromise<R_SubmissionVerdictVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/submission/status',
            query: {
                'submissionId': submissionId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
