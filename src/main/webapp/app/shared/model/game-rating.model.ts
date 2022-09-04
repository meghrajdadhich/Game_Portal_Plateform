export interface IGameRating {
  id?: number;
  gameid?: number;
  rating?: number;
  timestamp?: number;
}

export const defaultValue: Readonly<IGameRating> = {};
