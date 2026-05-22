"use client";

import {
  LineChart,
  Line,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

const data = [
  { month: "Jan", balance: 4000 },
  { month: "Feb", balance: 7000 },
  { month: "Mar", balance: 5000 },
  { month: "Apr", balance: 9000 },
  { month: "May", balance: 12000 },
  { month: "Jun", balance: 15000 },
];

export default function FinancialChart() {
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
      </div>
    </div>
  );
}