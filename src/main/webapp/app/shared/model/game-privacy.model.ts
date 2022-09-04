export interface IGamePrivacy {
  id?: number;
  agreementDetails?: string;
  personalinfo?: string;
}

export const defaultValue: Readonly<IGamePrivacy> = {};
