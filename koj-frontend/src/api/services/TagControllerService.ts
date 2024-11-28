/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_List_TagVO_ } from '../models/R_List_TagVO_';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class TagControllerService {
    /**
     * getTagList
     * @returns R_List_TagVO_ OK
     * @throws ApiError
     */
    public static getTagListUsingGet(): CancelablePromise<R_List_TagVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/tag/list',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
