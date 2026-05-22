"use client";

import { Bell, LogOut } from "lucide-react";

import { useRouter } from "next/navigation";

import { useAuth } from "@/app/context/AuthContext";

export default function Navbar() {
  const { user, logout } = useAuth();

  const router = useRouter();

  const handleLogout = () => {
    logout();

    router.push("/login");
  };

  return (
    <header className="h-20 border-b border-[#1E293B] bg-[#0F172A] flex items-center justify-between px-6">
      
      <div>
        <h2 className="text-2xl font-bold text-white">
          Dashboard
        </h2>

        <p className="text-slate-400 text-sm">
          Welcome back, {user?.name}
        </p>
      </div>

      <div className="flex items-center gap-4">
        
        <button className="bg-[#111827] p-3 rounded-xl hover:bg-[#1E293B] transition">
          <Bell size={20} />
        </button>

        <button
          onClick={handleLogout}
          className="flex items-center gap-2 bg-[#14B8A6] hover:bg-[#2DD4BF] px-4 py-2 rounded-xl text-black font-semibold transition"
        >
          <LogOut size={18} />
          Logout
        </button>
      </div>
    </header>
  );
}