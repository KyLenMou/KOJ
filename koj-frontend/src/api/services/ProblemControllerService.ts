/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_Page_ProblemsetVO_ } from '../models/R_Page_ProblemsetVO_';
import type { R_ProblemDetailVO_ } from '../models/R_ProblemDetailVO_';
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
     * getProblemDetailVO
     * @param problemDisplayId problemDisplayId
     * @returns R_ProblemDetailVO_ OK
     * @throws ApiError
     */
    public static getProblemDetailVoUsingGet(
        problemDisplayId: string,
    ): CancelablePromise<R_ProblemDetailVO_> {
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
