"use client";

import { useState } from "react";
import Link from "next/link";
import axios from "@/app/utils/axios";

export default function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [done, setDone] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async () => {
    setError("");
    setLoading(true);

    try {
      await axios.post("/api/auth/forgot-password", { email });
      setDone(true);
    } catch {
      // El backend no debería filtrar si el email existe o no,
      // así que mostramos el mismo mensaje de éxito igual.
      setDone(true);
    } finally {
      setLoading(false);
    }
  };

  if (done) {
    return (
      <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
        <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl text-center">
          <h1 className="text-2xl font-bold mb-4">Revisá tu email</h1>

          <p className="text-slate-400 mb-6">
            Si {email} está registrado, te enviamos un link para restablecer
            tu contraseña.
          </p>

          <Link
            href="/login"
            className="block w-full bg-[#14B8A6] text-black p-3 rounded-xl font-semibold text-center"
          >
            Volver a Login
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
      <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl">
        <h1 className="text-3xl font-bold mb-2 text-center">
          Olvidé mi contraseña
        </h1>

        <p className="text-slate-400 text-sm text-center mb-6">
          Te mandamos un link para restablecerla.
        </p>

        {error && (
          <div className="bg-red-500/10 border border-red-500 text-red-400 text-sm rounded-xl p-3 mb-4">
            {error}
          </div>
        )}

        <input
          type="email"
          className="w-full p-3 mb-4 bg-gray-700 rounded"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <button
          onClick={handleSubmit}
          disabled={loading || !email}
          className="w-full bg-[#14B8A6] hover:bg-[#0D9488] text-black p-3 rounded-xl font-semibold transition disabled:opacity-50"
        >
          {loading ? "Enviando..." : "Enviar link"}
        </button>

        <p className="text-sm text-slate-400 text-center mt-4">
          <Link href="/login" className="text-[#14B8A6]">
            Volver a Login
          </Link>
        </p>
      </div>
    </div>
  );
}