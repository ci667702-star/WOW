import { useEffect, useState } from 'react'
import { getHealth } from '../api/client'

type ServerStatus = 'checking' | 'up' | 'down'

const MENU = ['새 게임', '이어하기', '설정', '도감']

export default function TitlePage() {
  const [status, setStatus] = useState<ServerStatus>('checking')

  useEffect(() => {
    getHealth()
      .then(() => setStatus('up'))
      .catch(() => setStatus('down'))
  }, [])

  return (
    <main className="flex min-h-screen flex-col items-center justify-center gap-10 px-4">
      <header className="text-center">
        <p className="text-sm tracking-[0.4em] text-paper-dim">玄龍水務公社</p>
        <h1 className="mt-3 text-5xl font-bold text-gold">수문장</h1>
        <p className="mt-2 text-sm tracking-[0.3em] text-paper-dim">WARDEN OF WATERS</p>
      </header>

      {/* 메뉴는 아직 기능이 없다. 해당 단계에서 연결한다. */}
      <nav className="flex w-full max-w-xs flex-col gap-3">
        {MENU.map((label) => (
          <button
            key={label}
            type="button"
            disabled
            className="rounded border border-gold/30 bg-ink-soft py-3 text-paper-dim"
          >
            {label} <span className="text-xs">(준비 중)</span>
          </button>
        ))}
      </nav>

      <footer className="text-xs text-paper-dim">
        서버 연결:{' '}
        {status === 'checking' && '확인 중…'}
        {status === 'up' && <span className="text-jade">정상</span>}
        {status === 'down' && <span className="text-cinnabar">실패 — 백엔드가 실행 중인지 확인하세요</span>}
      </footer>
    </main>
  )
}
