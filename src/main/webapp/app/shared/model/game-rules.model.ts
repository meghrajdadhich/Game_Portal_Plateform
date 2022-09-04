export interface IGameRules {
  id?: number;
  ruleName?: string;
  description?: string;
  defaultValue?: string;
}

export const defaultValue: Readonly<IGameRules> = {};
