import type { RoleType } from "@/common/RoleType";

export interface UserInfo {
  userId: string;
  username: string;
  role: RoleType;
}
export type UserState = UserInfo
