export interface IMembershipHistory {
  id?: number;
  memberId?: number;
  paymentId?: number;
  paymentDate?: number;
  expereDate?: number;
  memberStatus?: string;
}

export const defaultValue: Readonly<IMembershipHistory> = {};
