/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_Page_ProblemsetVO_ } from '../models/R_Page_ProblemsetVO_';
import type { R_ProblemInfoVO_ } from '../models/R_ProblemInfoVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ProblemControllerService {
    /**
     * listProblemsetVOFromEs
     * @param current current
     * @param pageSize pageSize
     * @param searchText searchText
     * @returns R_Page_ProblemsetVO_ OK
     * @throws ApiError
     */
    public static listProblemsetVoFromEsUsingGet(
        current: number,
        pageSize: number,
        searchText: string,
    ): CancelablePromise<R_Page_ProblemsetVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/problem',
            query: {
                'current': current,
                'pageSize': pageSize,
                'searchText': searchText,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getProblemDetail
     * @param problemDisplayId problemDisplayId
     * @returns R_ProblemInfoVO_ OK
     * @throws ApiError
     */
    public static getProblemDetailUsingGet(
        problemDisplayId: string,
    ): CancelablePromise<R_ProblemInfoVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/problem/detail',
            query: {
                'problemDisplayId': problemDisplayId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
