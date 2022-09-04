export interface IGameSupport {
  id?: number;
  gameQuestions?: string;
  accQuestions?: string;
  tecnicalQuestions?: string;
  financialQuestions?: string;
  rulesQuestions?: string;
}

export const defaultValue: Readonly<IGameSupport> = {};
