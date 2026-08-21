export type TipoTransaccion =
  | "DEPOSITO"
  | "RETIRO"
  | "TRANSFERENCIA_ENVIADA"
  | "TRANSFERENCIA_RECIBIDA";

export interface RecentTransaction {
  id: number;
  description: string;
  amount: number;
  type: TipoTransaccion;
  createdAt: string;
}

interface Props {
  transactions: RecentTransaction[];
}

const isIncome = (type: TipoTransaccion) =>
  type === "DEPOSITO" || type === "TRANSFERENCIA_RECIBIDA";

export default function RecentTransactions({ transactions }: Props) {
  return (
    <div className="bg-[#111827] border border-[#1E293B] rounded-3xl p-6">

      <div className="mb-6">
        <h2 className="text-xl font-semibold">
          Recent Transactions
        </h2>
      </div>

      {transactions.length === 0 ? (

        <p className="text-slate-400 text-sm">
          No transactions yet
        </p>

      ) : (

        <div className="space-y-4">

          {transactions.map((transaction) => (

            <div
              key={transaction.id}
              className="flex items-center justify-between border-b border-[#1E293B] pb-4"
            >

              <div>
                <p className="font-medium">
                  {transaction.description}
                </p>
              </div>

              <p
                className={`font-bold ${
                  isIncome(transaction.type)
                    ? "text-[#14B8A6]"
                    : "text-red-400"
                }`}
              >
                {isIncome(transaction.type) ? "+" : "-"}
                ${transaction.amount.toLocaleString()}
              </p>

            </div>
          ))}

        </div>
      )}
    </div>
  );
}