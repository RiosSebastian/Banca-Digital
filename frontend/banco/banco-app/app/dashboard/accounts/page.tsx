"use client";

import { useEffect, useState } from "react";

import axios from "@/app/utils/axios";

import AccountCard from "@/app/components/Card";

import TransferModal from "@/app/components/TransferModal";

interface Account {
  id: number;
  alias: string;
  cbu: string;
  balance: number;
  type: string;
}

export default function AccountsPage() {

  const [accounts, setAccounts] =
    useState<Account[]>([]);

  const [open, setOpen] =
    useState(false);

  useEffect(() => {

    axios
      .get("/accounts")
      .then((res) => {
        setAccounts(res.data);
      });

  }, []);

  return (
    <div className="space-y-8">

      <div className="flex items-center justify-between">

        <div>

          <h1 className="text-4xl font-bold">
            Accounts
          </h1>

          <p className="text-slate-400 mt-2">
            Manage your bank accounts
          </p>

        </div>

        <button className="bg-[#14B8A6] hover:bg-[#0D9488] text-black px-6 py-3 rounded-2xl font-semibold transition">

          + New Account

        </button>

      </div>

      <div className="grid grid-cols-1 xl:grid-cols-2 gap-6">

        {accounts.map((account) => (

          <AccountCard
            key={account.id}
            alias={account.alias}
            cbu={account.cbu}
            balance={account.balance}
            type={account.type}
            onTransfer={() => setOpen(true)}
          />

        ))}

      </div>

      <TransferModal
        open={open}
        onClose={() => setOpen(false)}
      />

    </div>
  );
}