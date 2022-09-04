export interface IGameTransaction {
  id?: number;
  transactionId?: number;
  userId?: number;
  transactionTimeStamp?: number;
}

export const defaultValue: Readonly<IGameTransaction> = {};
