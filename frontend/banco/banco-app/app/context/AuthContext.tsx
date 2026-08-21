"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import axios from "@/app/utils/axios";

interface User {
  id: number;
  name: string;
  email: string;
}

interface AuthContextType {
  token: string | null;
  user: User | null;
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

  const [user, setUser] = useState<User | null>(null);

  const [mounted, setMounted] = useState(false);

  const fetchCurrentUser = () => {
    axios
      .get("/users/me")
      .then((res) => {
        setUser({
          id: res.data.id,
          name: res.data.name,
          email: res.data.email,
        });
      })
      .catch(() => {
        setUser(null);
      });
  };

  useEffect(() => {

    const stored =
      localStorage.getItem("token");

    if (stored) {
      setToken(stored);
      fetchCurrentUser();
    }

    setMounted(true);

  }, []);

  const login = (jwt: string) => {

    localStorage.setItem("token", jwt);

    setToken(jwt);

    fetchCurrentUser();
  };

  const logout = () => {

    localStorage.removeItem("token");

    setToken(null);

    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
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