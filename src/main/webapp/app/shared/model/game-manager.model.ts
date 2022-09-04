export interface IGameManager {
  id?: number;
  name?: string;
  address?: string;
  contactNumber?: number;
}

export const defaultValue: Readonly<IGameManager> = {};
