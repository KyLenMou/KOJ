/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TagVO } from './TagVO';
export type ProblemInfoVO = {
    auth?: number;
    authorUserId?: string;
    caseVersion?: string;
    cfScore?: number;
    codeShare?: boolean;
    createTime?: string;
    descriptionText?: string;
    difficulty?: number;
    examples?: string;
    groupId?: number;
    id?: number;
    input?: string;
    ioScore?: number;
    isGroup?: boolean;
    isPublic?: number;
    isRemote?: boolean;
    isRemoveEndBlank?: boolean;
    isUploadCase?: boolean;
    judgeMode?: string;
    memoryLimit?: number;
    modifiedUserId?: string;
    noteText?: string;
    openCaseResult?: boolean;
    output?: string;
    problemDisplayId?: string;
    problemSource?: string;
    problemType?: string;
    spjCode?: string;
    spjLanguage?: string;
    stackLimit?: number;
    tags?: Array<TagVO>;
    timeLimit?: number;
    title?: string;
    updateTime?: string;
};

