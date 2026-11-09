// 백엔드 API 호출 공통 함수. 게임 규칙·판정은 모두 백엔드가 처리하고, 여기서는 요청과 응답 해석만 한다.

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'

/** 백엔드 global/response/ResultDto 와 같은 형식 */
export interface ResultDto<T> {
  success: boolean
  code: number
  errorCode?: string
  msg: string
  data?: T
}

export class ApiError extends Error {
  readonly code: number
  readonly errorCode: string | undefined

  constructor(code: number, errorCode: string | undefined, message: string) {
    super(message)
    this.code = code
    this.errorCode = errorCode
  }
}

export async function apiRequest<T>(path: string, init?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...init,
    headers: { 'Content-Type': 'application/json', ...init?.headers },
  })
  const body = (await response.json()) as ResultDto<T>
  if (!body.success) {
    throw new ApiError(body.code, body.errorCode, body.msg)
  }
  return body.data as T
}

export const getHealth = () => apiRequest<string>('/api/health')
