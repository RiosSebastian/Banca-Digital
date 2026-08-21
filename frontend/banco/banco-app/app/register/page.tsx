"use client";

import { useState } from "react";
import Link from "next/link";
import axios from "@/app/utils/axios";

export default function Register() {
  const [nombre, setNombre] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [errors, setErrors] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [done, setDone] = useState(false);

  const handleRegister = async () => {
    setErrors([]);
    setLoading(true);

    try {
      await axios.post("/api/auth/register", {
        nombre,
        email,
        password,
      });

      setDone(true);
    } catch (err: any) {
      const details = err?.response?.data?.details;
      const message = err?.response?.data?.message;

      if (Array.isArray(details) && details.length > 0) {
        setErrors(details);
      } else if (message) {
        setErrors([message]);
      } else {
        setErrors(["No se pudo completar el registro"]);
      }
    } finally {
      setLoading(false);
    }
  };

  if (done) {
    return (
      <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
        <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl text-center">
          <h1 className="text-2xl font-bold mb-4">
            Revisá tu email
          </h1>

          <p className="text-slate-400 mb-6">
            Te enviamos un link de verificación a {email}. Tenés que
            confirmarlo antes de poder iniciar sesión.
          </p>

          <Link
            href="/login"
            className="block w-full bg-[#14B8A6] text-black p-3 rounded-xl font-semibold text-center"
          >
            Ir a Login
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
      <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl">
        <h1 className="text-3xl font-bold mb-6 text-center">
          Crear cuenta
        </h1>

        {errors.length > 0 && (
          <div className="bg-red-500/10 border border-red-500 text-red-400 text-sm rounded-xl p-3 mb-4">
            <ul className="list-disc list-inside space-y-1">
              {errors.map((error, i) => (
                <li key={i}>{error}</li>
              ))}
            </ul>
          </div>
        )}

        <input
          className="w-full p-3 mb-3 bg-gray-700 rounded"
          placeholder="Nombre completo"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
        />

        <input
          type="email"
          className="w-full p-3 mb-3 bg-gray-700 rounded"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          className="w-full p-3 mb-1 bg-gray-700 rounded"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <p className="text-xs text-slate-500 mb-4">
          Mínimo 8 caracteres, con mayúscula, minúscula, número y un
          símbolo (@#$%^&amp;+=!).
        </p>

        <button
          onClick={handleRegister}
          disabled={loading}
          className="w-full bg-[#14B8A6] hover:bg-[#0D9488] text-black p-3 rounded-xl font-semibold transition disabled:opacity-50"
        >
          {loading ? "Creando cuenta..." : "Registrarme"}
        </button>

        <p className="text-sm text-slate-400 text-center mt-4">
          ¿Ya tenés cuenta?{" "}
          <Link href="/login" className="text-[#14B8A6]">
            Iniciar sesión
          </Link>
        </p>
      </div>
    </div>
  );
}
