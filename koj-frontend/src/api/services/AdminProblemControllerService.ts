/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProblemAddDTO } from '../models/ProblemAddDTO';
import type { R_Void_ } from '../models/R_Void_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AdminProblemControllerService {
    /**
     * addProblem
     * @param problemAddDto problemAddDTO
     * @returns R_Void_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static addProblemUsingPost(
        problemAddDto: ProblemAddDTO,
    ): CancelablePromise<R_Void_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/problem',
            body: problemAddDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
