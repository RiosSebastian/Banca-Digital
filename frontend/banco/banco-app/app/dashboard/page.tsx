"use client";

import FinanceCard from "@/app/components/FinanceCard";

import FinancialChart from "@/app/components/FinancialChart";

import RecentTransactions from "@/app/components/RecentTransactions";

export default function DashboardPage() {
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
          value="$24,580"
          description="+12% this month"
        />

        <FinanceCard
          title="Income"
          value="$8,420"
          description="+4.3%"
        />

        <FinanceCard
          title="Expenses"
          value="$3,120"
          description="-2.1%"
        />

        <FinanceCard
          title="Savings"
          value="$12,200"
          description="+18%"
        />

      </div>

      <div className="grid grid-cols-1 xl:grid-cols-3 gap-6">

        <div className="xl:col-span-2">
          <FinancialChart />
        </div>

        <div>
          <RecentTransactions />
        </div>

      </div>
    </div>
  );
}