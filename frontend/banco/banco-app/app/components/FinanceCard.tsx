interface Props {
  title: string;
  value: string;
  description: string;
}

export default function FinanceCard({
  title,
  value,
  description,
}: Props) {
  return (
    <div className="bg-[#111827] border border-[#1E293B] rounded-3xl p-6 hover:border-[#14B8A6] transition-all">

      <p className="text-slate-400 text-sm">
        {title}
      </p>

      <h2 className="text-3xl font-bold mt-3">
        {value}
      </h2>

      <p className="text-[#14B8A6] text-sm mt-2">
        {description}
      </p>
    </div>
  );
}