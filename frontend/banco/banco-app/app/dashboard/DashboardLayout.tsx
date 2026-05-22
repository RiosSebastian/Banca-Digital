import AppNavbar from '@/app/components/Navbar'
import Sidebar from '@/app/components/Sidebar'

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className='flex bg-black text-white'>
      <Sidebar />

      <div className='flex-1'>
        <AppNavbar />

        <main className='p-8'>
          {children}
        </main>
      </div>
    </div>
  )
}