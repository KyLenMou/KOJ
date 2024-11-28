/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Problem } from './Problem';
import type { ProblemCase } from './ProblemCase';
export type AdminEditProblemDTO = {
    problem?: Problem;
    tagIds?: Array<number>;
    testCases?: Array<ProblemCase>;
    uploadTestcaseDir?: string;
};

