"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

interface User {
  id: number;
  name: string;
  email: string;
}

interface AuthContextType {
  token: string | null;
  login: (jwt: string) => void;
  logout: () => void;
  mounted: boolean;
}

const AuthContext = createContext<AuthContextType>(
  {} as AuthContextType
);

export const AuthProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {

  const [token, setToken] = useState<string | null>(null);

  const [mounted, setMounted] = useState(false);

  useEffect(() => {

    const stored =
      localStorage.getItem("token");

    if (stored) {
      setToken(stored);
    }

    setMounted(true);

  }, []);

  const login = (jwt: string) => {

    localStorage.setItem("token", jwt);

    setToken(jwt);
  };

  const logout = () => {

    localStorage.removeItem("token");

    setToken(null);
  };

  return (
    <AuthContext.Provider
      value={{
        token,
        login,
        logout,
        mounted,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () =>
  useContext(AuthContext);