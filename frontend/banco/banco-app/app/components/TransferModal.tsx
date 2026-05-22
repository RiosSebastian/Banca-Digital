"use client";

import { useState } from "react";

import axios from "@/app/utils/axios";

interface Props {
  open: boolean;
  onClose: () => void;
}

export default function TransferModal({
  open,
  onClose,
}: Props) {

  const [toAccount, setToAccount] =
    useState("");

  const [amount, setAmount] =
    useState("");

  const handleTransfer = async () => {

    try {

      await axios.post("/transactions/transfer", {
        toAccount,
        amount: Number(amount),
      });

      alert("Transfer completed");

      onClose();

    } catch {

      alert("Transfer error");
    }
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">

      <div className="bg-[#111827] border border-[#1E293B] rounded-3xl p-8 w-full max-w-md">

        <h2 className="text-2xl font-bold mb-6">
          New Transfer
        </h2>

        <div className="space-y-4">

          <input
            placeholder="Destination CBU"
            value={toAccount}
            onChange={(e) =>
              setToAccount(e.target.value)
            }
            className="w-full bg-[#0F172A] border border-[#1E293B] rounded-2xl px-4 py-3 outline-none focus:border-[#14B8A6]"
          />

          <input
            placeholder="Amount"
            value={amount}
            onChange={(e) =>
              setAmount(e.target.value)
            }
            className="w-full bg-[#0F172A] border border-[#1E293B] rounded-2xl px-4 py-3 outline-none focus:border-[#14B8A6]"
          />

        </div>

        <div className="flex gap-4 mt-8">

          <button
            onClick={onClose}
            className="flex-1 bg-[#1E293B] py-3 rounded-2xl"
          >
            Cancel
          </button>

          <button
            onClick={handleTransfer}
            className="flex-1 bg-[#14B8A6] text-black font-semibold py-3 rounded-2xl"
          >
            Send
          </button>

        </div>
      </div>
    </div>
  );
}