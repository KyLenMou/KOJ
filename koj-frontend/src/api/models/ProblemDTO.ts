/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Problem } from './Problem';
import type { ProblemCase } from './ProblemCase';
import type { Tag } from './Tag';
export type ProblemDTO = {
    problem?: Problem;
    tags?: Array<Tag>;
    testCases?: Array<ProblemCase>;
    uploadTestcaseDir?: string;
};

