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
  tipo: string;
}

export default function AccountsPage() {

  const [accounts, setAccounts] =
    useState<Account[]>([]);

  const [open, setOpen] =
    useState(false);

  const [selectedAccountId, setSelectedAccountId] =
    useState<number | null>(null);

  const loadAccounts = () => {
    axios
      .get("/accounts")
      .then((res) => {
        setAccounts(res.data);
      });
  };

  useEffect(() => {
    loadAccounts();
  }, []);

  return (
    <div className="space-y-8">

      <div>

        <h1 className="text-4xl font-bold">
          Accounts
        </h1>

        <p className="text-slate-400 mt-2">
          Manage your bank accounts
        </p>

      </div>

      <div className="grid grid-cols-1 xl:grid-cols-2 gap-6">

        {accounts.map((account) => (

          <AccountCard
            key={account.id}
            alias={account.alias}
            cbu={account.cbu}
            balance={account.balance}
            type={account.tipo}
            onTransfer={() => {
              setSelectedAccountId(account.id);
              setOpen(true);
            }}
          />

        ))}

      </div>

      <TransferModal
        open={open}
        onClose={() => setOpen(false)}
        fromAccountId={selectedAccountId}
        onSuccess={loadAccounts}
      />

    </div>
  );
}