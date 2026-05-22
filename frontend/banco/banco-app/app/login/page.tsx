"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import axios from "@/app/utils/axios";
import { useAuth } from "@/app/context/AuthContext";

export default function Login() {
  const { login } = useAuth();
  const router = useRouter();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const res = await axios.post("/api/auth/login", {
        email,
        password,
      });

      await login(res.data.token);
      router.push("/dashboard");
    } catch {
      alert("Credenciales incorrectas");
    }
  };

  return (
    <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
      <div className="bg-gray-800 p-8 rounded-2xl w-96 shadow-xl">
        <h1 className="text-3xl font-bold mb-6 text-center">Bank App</h1>

        <input
          className="w-full p-3 mb-3 bg-gray-700 rounded"
          placeholder="Email"
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          className="w-full p-3 mb-4 bg-gray-700 rounded"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          onClick={handleLogin}
          className="w-full bg-blue-600 hover:bg-blue-700 p-3 rounded-xl transition"
        >
          Ingresar
        </button>
      </div>
    </div>
  );
}