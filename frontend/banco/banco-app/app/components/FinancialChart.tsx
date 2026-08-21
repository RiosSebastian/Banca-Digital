"use client";

import {
  LineChart,
  Line,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

export interface BalancePoint {
  month: string;
  balance: number;
}

interface Props {
  data: BalancePoint[];
}

export default function FinancialChart({ data }: Props) {
  return (
    <div className="bg-[#111827] border border-[#1E293B] rounded-3xl p-6">

      <div className="mb-6">
        <h2 className="text-xl font-semibold">
          Financial Overview
        </h2>

        <p className="text-slate-400 text-sm">
          Balance evolution
        </p>
      </div>

      <div className="h-80">

        {data.length === 0 ? (

          <div className="h-full flex items-center justify-center text-slate-400 text-sm">
            No hay movimientos todavía
          </div>

        ) : (

          <ResponsiveContainer
            width="100%"
            height="100%"
          >

            <LineChart data={data}>

              <XAxis dataKey="month" />

              <YAxis />

              <Tooltip />

              <Line
                type="monotone"
                dataKey="balance"
                stroke="#14B8A6"
                strokeWidth={3}
              />

            </LineChart>

          </ResponsiveContainer>
        )}
      </div>
    </div>
  );
}