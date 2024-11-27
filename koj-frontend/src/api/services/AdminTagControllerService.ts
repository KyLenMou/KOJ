/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { R_List_Tag_ } from '../models/R_List_Tag_';
import type { R_long_ } from '../models/R_long_';
import type { R_Tag_ } from '../models/R_Tag_';
import type { R_Void_ } from '../models/R_Void_';
import type { Tag } from '../models/Tag';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AdminTagControllerService {
    /**
     * getTagById
     * @param id id
     * @returns R_Tag_ OK
     * @throws ApiError
     */
    public static getTagByIdUsingGet(
        id: number,
    ): CancelablePromise<R_Tag_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/admin/tag',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * addTag
     * @param tagName tagName
     * @returns R_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static addTagUsingPost(
        tagName: string,
    ): CancelablePromise<R_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/tag',
            query: {
                'tagName': tagName,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * updateTag
     * @param tag tag
     * @returns R_Void_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateTagUsingPut(
        tag: Tag,
    ): CancelablePromise<R_Void_ | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/admin/tag',
            body: tag,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deleteTag
     * @param id id
     * @returns R_Void_ OK
     * @throws ApiError
     */
    public static deleteTagUsingDelete(
        id: number,
    ): CancelablePromise<R_Void_> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/admin/tag',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }
    /**
     * listAllTag
     * @returns R_List_Tag_ OK
     * @throws ApiError
     */
    public static listAllTagUsingGet(): CancelablePromise<R_List_Tag_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/admin/tag/list',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
