export interface IMembership {
  id?: number;
  memberid?: number;
  name?: string;
  gameTitle?: string;
}

export const defaultValue: Readonly<IMembership> = {};
