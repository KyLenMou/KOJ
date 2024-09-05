/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageDTO } from '../models/PageDTO';
import type { R_Page_ProblemsetVO_ } from '../models/R_Page_ProblemsetVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ProblemControllerService {
    /**
     * listProblemsetVOByPage
     * @param pageDto pageDTO
     * @returns R_Page_ProblemsetVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listProblemsetVoByPageUsingPost(
        pageDto: PageDTO,
    ): CancelablePromise<R_Page_ProblemsetVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/problem/list',
            body: pageDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
