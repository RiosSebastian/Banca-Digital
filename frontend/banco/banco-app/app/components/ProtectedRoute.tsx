"use client";

import { useAuth } from "@/app/context/AuthContext";

import { useRouter } from "next/navigation";

import { useEffect } from "react";

export default function ProtectedRoute({
  children,
}: {
  children: React.ReactNode;
}) {

  const {
    token,
    mounted,
  } = useAuth();

  const router = useRouter();

  useEffect(() => {

    if (mounted && !token) {
      router.push("/login");
    }

  }, [mounted, token, router]);

  if (!mounted) {
    return null;
  }

  if (!token) {
    return null;
  }

  return children;
}