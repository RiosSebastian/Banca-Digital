import Link from "next/link";

export default function HomePage() {
  return (
    <main className="min-h-screen bg-[#0F172A] text-white flex items-center justify-center">
      
      <div className="text-center">

        <h1 className="text-5xl font-bold mb-6 text-[#14B8A6]">
          Rios Bank
        </h1>

        <p className="text-slate-400 mb-8">
          Digital Banking Platform
        </p>

        <div className="flex gap-4 justify-center">

          <Link
            href="/login"
            className="bg-[#14B8A6] text-black px-6 py-3 rounded-2xl font-semibold"
          >
            Login
          </Link>

          <Link
            href="/dashboard"
            className="bg-[#111827] px-6 py-3 rounded-2xl"
          >
            Dashboard
          </Link>

        </div>
      </div>
    </main>
  );
}