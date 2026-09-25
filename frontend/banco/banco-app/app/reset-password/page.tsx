"use client";

import { Suspense, useState } from "react";
import { useSearchParams } from "next/navigation";
import Link from "next/link";
import axios from "@/app/utils/axios";

function ResetPasswordForm() {
  const searchParams = useSearchParams();
  const token = searchParams.get("token");

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [done, setDone] = useState(false);

  const handleSubmit = async () => {
    setErrors([]);

    if (!token) {
      setErrors(["El link no es válido: falta el token"]);
      return;
    }

    if (newPassword !== confirmPassword) {
      setErrors(["Las contraseñas no coinciden"]);
      return;
    }

    setLoading(true);

    try {
      await axios.post("/api/auth/reset-password", {
        token,
        newPassword,
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
        setErrors(["No se pudo actualizar la contraseña"]);
      }
    } finally {
      setLoading(false);
    }
  };

  if (!token) {
    return (
      <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
        <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl text-center">
          <h1 className="text-2xl font-bold mb-4">Link inválido</h1>

          <p className="text-slate-400 mb-6">
            Este link no tiene un token de recuperación. Pedí uno nuevo desde
            la pantalla de login.
          </p>

          <Link
            href="/forgot-password"
            className="block w-full bg-[#14B8A6] text-black p-3 rounded-xl font-semibold text-center"
          >
            Pedir un link nuevo
          </Link>
        </div>
      </div>
    );
  }

  if (done) {
    return (
      <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
        <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl text-center">
          <h1 className="text-2xl font-bold mb-4">Contraseña actualizada</h1>

          <p className="text-slate-400 mb-6">
            Ya podés iniciar sesión con tu nueva contraseña.
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
          Nueva contraseña
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
          type="password"
          className="w-full p-3 mb-3 bg-gray-700 rounded"
          placeholder="Nueva contraseña"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
        />

        <input
          type="password"
          className="w-full p-3 mb-1 bg-gray-700 rounded"
          placeholder="Confirmar contraseña"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
        />

        <p className="text-xs text-slate-500 mb-4">
          Mínimo 8 caracteres, con mayúscula, minúscula, número y un símbolo
          (@#$%^&amp;+=!).
        </p>

        <button
          onClick={handleSubmit}
          disabled={loading}
          className="w-full bg-[#14B8A6] hover:bg-[#0D9488] text-black p-3 rounded-xl font-semibold transition disabled:opacity-50"
        >
          {loading ? "Actualizando..." : "Actualizar contraseña"}
        </button>
      </div>
    </div>
  );
}

export default function ResetPassword() {
  return (
    <Suspense fallback={null}>
      <ResetPasswordForm />
    </Suspense>
  );
}