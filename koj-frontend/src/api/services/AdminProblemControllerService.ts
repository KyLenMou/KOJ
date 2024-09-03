/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProblemDTO } from '../models/ProblemDTO';
import type { R_Void_ } from '../models/R_Void_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AdminProblemControllerService {
    /**
     * saveProblem
     * @param problemDto problemDTO
     * @returns R_Void_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static saveProblemUsingPost(
        problemDto: ProblemDTO,
    ): CancelablePromise<R_Void_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/problem',
            body: problemDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
