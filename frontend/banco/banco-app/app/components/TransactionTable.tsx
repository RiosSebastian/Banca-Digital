interface Transaction {
  id: number;
  type: "INCOME" | "EXPENSE";
  amount: number;
  description: string;
  createdAt: string;
  status: string;
}

interface Props {
  transactions: Transaction[];
}

export default function TransactionTable({
  transactions,
}: Props) {
  return (
    <div className="overflow-x-auto">

      <table className="w-full">

        <thead>
          <tr className="text-left border-b border-[#1E293B] text-slate-400">

            <th className="pb-4">
              Description
            </th>

            <th className="pb-4">
              Status
            </th>

            <th className="pb-4">
              Date
            </th>

            <th className="pb-4 text-right">
              Amount
            </th>
          </tr>
        </thead>

        <tbody>

          {transactions.map((transaction) => (

            <tr
              key={transaction.id}
              className="border-b border-[#1E293B]/50 hover:bg-[#1E293B]/30 transition"
            >

              <td className="py-5">
                <div>
                  <p className="font-medium">
                    {transaction.description}
                  </p>

                  <p className="text-sm text-slate-400">
                    {transaction.type}
                  </p>
                </div>
              </td>

              <td className="py-5">

                <span className="bg-[#14B8A6]/20 text-[#14B8A6] px-3 py-1 rounded-full text-xs">
                  {transaction.status}
                </span>

              </td>

              <td className="py-5 text-slate-400">
                {new Date(
                  transaction.createdAt
                ).toLocaleDateString()}
              </td>

              <td
                className={`py-5 text-right font-bold ${
                  transaction.type === "INCOME"
                    ? "text-[#14B8A6]"
                    : "text-red-400"
                }`}
              >
                {transaction.type === "INCOME"
                  ? "+"
                  : "-"}
                ${transaction.amount.toLocaleString()}
              </td>

            </tr>
          ))}

        </tbody>
      </table>
    </div>
  );
}