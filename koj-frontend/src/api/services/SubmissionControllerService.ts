/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_List_SubmissionVerdictVO_ } from '../models/R_List_SubmissionVerdictVO_';
import type { R_long_ } from '../models/R_long_';
import type { R_Page_SubmissionListVO_ } from '../models/R_Page_SubmissionListVO_';
import type { R_SubmissionDetailVO_ } from '../models/R_SubmissionDetailVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SubmissionControllerService {
    /**
     * listSubmissionByPage
     * @param current current
     * @param size size
     * @param language language
     * @param onlyMine onlyMine
     * @param problemDisplayId problemDisplayId
     * @param problemId problemId
     * @param userId userId
     * @param username username
     * @param verdict verdict
     * @returns R_Page_SubmissionListVO_ OK
     * @throws ApiError
     */
    public static listSubmissionByPageUsingGet(
        current: number,
        size: number,
        language?: string,
        onlyMine?: boolean,
        problemDisplayId?: string,
        problemId?: number,
        userId?: string,
        username?: string,
        verdict?: number,
    ): CancelablePromise<R_Page_SubmissionListVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/submission',
            query: {
                'current': current,
                'language': language,
                'onlyMine': onlyMine,
                'problemDisplayId': problemDisplayId,
                'problemId': problemId,
                'size': size,
                'userId': userId,
                'username': username,
                'verdict': verdict,
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
     * getQueueWaiting
     * @param submissionId submissionId
     * @returns R_long_ OK
     * @throws ApiError
     */
    public static getQueueWaitingUsingGet(
        submissionId: number,
    ): CancelablePromise<R_long_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/submission/queue-waiting',
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
     * getSubmissionVerdictList
     * @param submissionId submissionId
     * @returns R_List_SubmissionVerdictVO_ OK
     * @throws ApiError
     */
    public static getSubmissionVerdictListUsingGet(
        submissionId: Array<number>,
    ): CancelablePromise<R_List_SubmissionVerdictVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/submission/verdict',
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
