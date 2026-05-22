"use client";

import Link from "next/link";

import {
  LayoutDashboard,
  Wallet,
  ArrowLeftRight,
  Settings,
} from "lucide-react";

export default function Sidebar() {
  return (
    <aside className="hidden md:flex w-72 min-h-screen bg-[#111827] border-r border-[#1E293B] flex-col p-6">
      
      <div className="mb-10">
        <h1 className="text-3xl font-bold text-[#14B8A6]">
          RiosBank
        </h1>

        <p className="text-sm text-slate-400">
          Digital Banking
        </p>
      </div>

      <nav className="flex flex-col gap-3">
        
        <Link
          href="/dashboard"
          className="flex items-center gap-3 p-3 rounded-xl hover:bg-[#1E293B] transition"
        >
          <LayoutDashboard size={20} />
          Dashboard
        </Link>

        <Link
          href="/dashboard/accounts"
          className="flex items-center gap-3 p-3 rounded-xl hover:bg-[#1E293B] transition"
        >
          <Wallet size={20} />
          Accounts
        </Link>

        <Link
          href="/dashboard/transactions"
          className="flex items-center gap-3 p-3 rounded-xl hover:bg-[#1E293B] transition"
        >
          <ArrowLeftRight size={20} />
          Transactions
        </Link>

        <Link
          href="/dashboard/settings"
          className="flex items-center gap-3 p-3 rounded-xl hover:bg-[#1E293B] transition"
        >
          <Settings size={20} />
          Settings
        </Link>
      </nav>
    </aside>
  );
}