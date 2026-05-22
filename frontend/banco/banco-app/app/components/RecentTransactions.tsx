const transactions = [
  {
    id: 1,
    title: "Netflix",
    amount: -20,
  },
  {
    id: 2,
    title: "Salary",
    amount: 2500,
  },
  {
    id: 3,
    title: "Amazon",
    amount: -120,
  },
];

export default function RecentTransactions() {
  return (
    <div className="bg-[#111827] border border-[#1E293B] rounded-3xl p-6">

      <div className="mb-6">
        <h2 className="text-xl font-semibold">
          Recent Transactions
        </h2>
      </div>

      <div className="space-y-4">

        {transactions.map((transaction) => (

          <div
            key={transaction.id}
            className="flex items-center justify-between border-b border-[#1E293B] pb-4"
          >

            <div>
              <p className="font-medium">
                {transaction.title}
              </p>
            </div>

            <p
              className={`font-bold ${
                transaction.amount > 0
                  ? "text-[#14B8A6]"
                  : "text-red-400"
              }`}
            >
              {transaction.amount > 0 ? "+" : ""}
              ${transaction.amount}
            </p>

          </div>
        ))}

      </div>
    </div>
  );
}