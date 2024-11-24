/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AdminEditProblemDTO } from '../models/AdminEditProblemDTO';
import type { R_AdminEditProblemDTO_ } from '../models/R_AdminEditProblemDTO_';
import type { R_Page_AdminProblemVO_ } from '../models/R_Page_AdminProblemVO_';
import type { R_Void_ } from '../models/R_Void_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AdminProblemControllerService {
    /**
     * getEditProblem
     * @param problemId problemId
     * @returns R_AdminEditProblemDTO_ OK
     * @throws ApiError
     */
    public static getEditProblemUsingGet(
        problemId: number,
    ): CancelablePromise<R_AdminEditProblemDTO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/admin/problem',
            query: {
                'problemId': problemId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * editProblem
     * @param adminEditProblemDto adminEditProblemDTO
     * @param isUpdate isUpdate
     * @returns R_Void_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static editProblemUsingPost(
        adminEditProblemDto: AdminEditProblemDTO,
        isUpdate: boolean,
    ): CancelablePromise<R_Void_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/problem',
            query: {
                'isUpdate': isUpdate,
            },
            body: adminEditProblemDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deleteProblem
     * @param problemId problemId
     * @returns R_Void_ OK
     * @throws ApiError
     */
    public static deleteProblemUsingDelete(
        problemId: number,
    ): CancelablePromise<R_Void_> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/admin/problem',
            query: {
                'problemId': problemId,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }
    /**
     * listProblemByPage
     * @param current current
     * @param size size
     * @returns R_Page_AdminProblemVO_ OK
     * @throws ApiError
     */
    public static listProblemByPageUsingGet(
        current: number,
        size: number,
    ): CancelablePromise<R_Page_AdminProblemVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/admin/problem/list',
            query: {
                'current': current,
                'size': size,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
