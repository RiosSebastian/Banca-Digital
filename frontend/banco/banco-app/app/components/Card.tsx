"use client";

import { Copy, Send } from "lucide-react";

interface Props {
  alias: string;
  cbu: string;
  balance: number;
  type: string;
  onTransfer: () => void;
}

export default function AccountCard({
  alias,
  cbu,
  balance,
  type,
  onTransfer,
}: Props) {

  const copyCBU = async () => {
    await navigator.clipboard.writeText(cbu);

    alert("CBU copied");
  };

  return (
    <div className="bg-gradient-to-br from-[#0F172A] to-[#111827] border border-[#1E293B] rounded-3xl p-6 hover:border-[#14B8A6] transition-all">

      <div className="flex items-start justify-between">

        <div>

          <p className="text-slate-400 text-sm">
            {type}
          </p>

          <h2 className="text-2xl font-bold mt-2">
            ${balance.toLocaleString()}
          </h2>

        </div>

        <div className="bg-[#14B8A6]/20 text-[#14B8A6] px-3 py-1 rounded-full text-sm">
          Active
        </div>

      </div>

      <div className="mt-8">

        <p className="text-slate-400 text-sm">
          Alias
        </p>

        <p className="font-semibold mt-1">
          {alias}
        </p>

      </div>

      <div className="mt-4">

        <p className="text-slate-400 text-sm">
          CBU
        </p>

        <div className="flex items-center justify-between mt-1">

          <p className="font-medium text-sm">
            {cbu}
          </p>

          <button
            onClick={copyCBU}
            className="text-[#14B8A6] hover:scale-110 transition"
          >
            <Copy size={18} />
          </button>

        </div>
      </div>

      <button
        onClick={onTransfer}
        className="mt-8 w-full bg-[#14B8A6] hover:bg-[#0D9488] text-black font-semibold py-3 rounded-2xl flex items-center justify-center gap-2 transition"
      >

        <Send size={18} />

        Transfer

      </button>
    </div>
  );
}