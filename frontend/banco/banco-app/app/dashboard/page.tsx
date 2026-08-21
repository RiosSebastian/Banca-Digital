"use client";

import { useEffect, useState } from "react";

import axios from "@/app/utils/axios";

import FinanceCard from "@/app/components/FinanceCard";

import FinancialChart, { BalancePoint } from "@/app/components/FinancialChart";

import RecentTransactions, {
  RecentTransaction,
} from "@/app/components/RecentTransactions";

interface DashboardData {
  totalBalance: number;
  monthlyIncome: number;
  monthlyExpenses: number;
  savings: number;
  history: BalancePoint[];
  recentTransactions: RecentTransaction[];
}

const formatCurrency = (value: number) =>
  `$${value.toLocaleString(undefined, {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  })}`;

export default function DashboardPage() {
  const [data, setData] = useState<DashboardData | null>(null);

  useEffect(() => {
    axios.get("/dashboard").then((res) => {
      setData(res.data);
    });
  }, []);

  if (!data) {
    return (
      <div className="text-slate-400">
        Loading dashboard...
      </div>
    );
  }

  return (
    <div className="space-y-8">

      <div>

        <h1 className="text-4xl font-bold">
          Dashboard
        </h1>

        <p className="text-slate-400 mt-2">
          Welcome back to your bank
        </p>

      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6">

        <FinanceCard
          title="Total Balance"
          value={formatCurrency(data.totalBalance)}
          description="Across all your accounts"
        />

        <FinanceCard
          title="Income"
          value={formatCurrency(data.monthlyIncome)}
          description="This month"
        />

        <FinanceCard
          title="Expenses"
          value={formatCurrency(data.monthlyExpenses)}
          description="This month"
        />

        <FinanceCard
          title="Savings"
          value={formatCurrency(data.savings)}
          description="Balance minus expenses"
        />

      </div>

      <div className="grid grid-cols-1 xl:grid-cols-3 gap-6">

        <div className="xl:col-span-2">
          <FinancialChart data={data.history} />
        </div>

        <div>
          <RecentTransactions transactions={data.recentTransactions} />
        </div>

      </div>
    </div>
  );
}