export type TipoTransaccion =
  | "DEPOSITO"
  | "RETIRO"
  | "TRANSFERENCIA_ENVIADA"
  | "TRANSFERENCIA_RECIBIDA";

interface Transaction {
  id: number;
  monto: number;
  fecha: string;
  tipo: TipoTransaccion;
  descripcion: string;
}

interface Props {
  transactions: Transaction[];
}

const isIncome = (tipo: TipoTransaccion) =>
  tipo === "DEPOSITO" || tipo === "TRANSFERENCIA_RECIBIDA";

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
              Type
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
                <p className="font-medium">
                  {transaction.descripcion}
                </p>
              </td>

              <td className="py-5">

                <span className="bg-[#14B8A6]/20 text-[#14B8A6] px-3 py-1 rounded-full text-xs">
                  {transaction.tipo}
                </span>

              </td>

              <td className="py-5 text-slate-400">
                {new Date(
                  transaction.fecha
                ).toLocaleDateString()}
              </td>

              <td
                className={`py-5 text-right font-bold ${
                  isIncome(transaction.tipo)
                    ? "text-[#14B8A6]"
                    : "text-red-400"
                }`}
              >
                {isIncome(transaction.tipo) ? "+" : "-"}
                ${transaction.monto.toLocaleString()}
              </td>

            </tr>
          ))}

        </tbody>
      </table>
    </div>
  );
}