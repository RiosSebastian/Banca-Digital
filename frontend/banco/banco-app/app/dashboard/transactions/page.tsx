"use client";

import { useEffect, useState } from "react";

import axios from "@/app/utils/axios";

import TransactionTable from "@/app/components/TransactionTable";

import { Search } from "lucide-react";

interface Transaction {
  id: number;
  type: "INCOME" | "EXPENSE";
  amount: number;
  description: string;
  createdAt: string;
  status: string;
}

export default function TransactionsPage() {

  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const [search, setSearch] = useState("");

  const [filter, setFilter] = useState("ALL");

  const [page, setPage] = useState(0);

  useEffect(() => {

    axios
      .get("/transactions", {
        params: {
          page,
          search,
          type:
            filter === "ALL"
              ? null
              : filter,
        },
      })
      .then((res) => {
        setTransactions(res.data.content);
      });

  }, [page, search, filter]);

  return (
    <div className="space-y-8">

      <div>

        <h1 className="text-3xl font-bold">
          Transactions
        </h1>

        <p className="text-slate-400 mt-1">
          View all your banking activity
        </p>
      </div>

      <div className="flex flex-col xl:flex-row gap-4 xl:items-center xl:justify-between">

        <div className="relative w-full xl:w-96">

          <Search
            className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400"
            size={18}
          />

          <input
            value={search}
            onChange={(e) =>
              setSearch(e.target.value)
            }
            placeholder="Search transaction..."
            className="w-full bg-[#111827] border border-[#1E293B] rounded-2xl pl-12 pr-4 py-4 outline-none focus:border-[#14B8A6]"
          />
        </div>

        <div className="flex gap-3">

          <button
            onClick={() => setFilter("ALL")}
            className={`px-4 py-2 rounded-xl transition ${
              filter === "ALL"
                ? "bg-[#14B8A6] text-black"
                : "bg-[#111827]"
            }`}
          >
            All
          </button>

          <button
            onClick={() => setFilter("INCOME")}
            className={`px-4 py-2 rounded-xl transition ${
              filter === "INCOME"
                ? "bg-[#14B8A6] text-black"
                : "bg-[#111827]"
            }`}
          >
            Income
          </button>

          <button
            onClick={() => setFilter("EXPENSE")}
            className={`px-4 py-2 rounded-xl transition ${
              filter === "EXPENSE"
                ? "bg-[#14B8A6] text-black"
                : "bg-[#111827]"
            }`}
          >
            Expense
          </button>

        </div>
      </div>

      <div className="bg-[#111827] border border-[#1E293B] rounded-3xl p-6">

        <TransactionTable
          transactions={transactions}
        />

      </div>

      <div className="flex justify-center gap-4">

        <button
          onClick={() =>
            setPage((prev) =>
              Math.max(prev - 1, 0)
            )
          }
          className="bg-[#111827] px-5 py-3 rounded-xl"
        >
          Prev
        </button>

        <button
          className="bg-[#14B8A6] text-black px-5 py-3 rounded-xl font-bold"
        >
          {page + 1}
        </button>

        <button
          onClick={() =>
            setPage((prev) => prev + 1)
          }
          className="bg-[#111827] px-5 py-3 rounded-xl"
        >
          Next
        </button>
      </div>
    </div>
  );
}