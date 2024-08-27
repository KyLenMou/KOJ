/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageDTO } from '../models/PageDTO';
import type { R_boolean_ } from '../models/R_boolean_';
import type { R_long_ } from '../models/R_long_';
import type { R_Page_Tag_ } from '../models/R_Page_Tag_';
import type { R_Tag_ } from '../models/R_Tag_';
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
     * @param tag tag
     * @returns R_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static addTagUsingPost(
        tag: Tag,
    ): CancelablePromise<R_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
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
     * updateTag
     * @param tag tag
     * @returns R_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateTagUsingPut(
        tag: Tag,
    ): CancelablePromise<R_boolean_ | any> {
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
     * @returns R_boolean_ OK
     * @throws ApiError
     */
    public static deleteTagUsingDelete(
        id: number,
    ): CancelablePromise<R_boolean_> {
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
     * listTagByPage
     * @param pageDto pageDTO
     * @returns R_Page_Tag_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listTagByPageUsingPost(
        pageDto: PageDTO,
    ): CancelablePromise<R_Page_Tag_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/admin/tag/list',
            body: pageDto,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
