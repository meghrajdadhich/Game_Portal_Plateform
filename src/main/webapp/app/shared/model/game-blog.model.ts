export interface IGameBlog {
  id?: number;
  title?: string;
  category?: string;
  description?: string;
}

export const defaultValue: Readonly<IGameBlog> = {};
