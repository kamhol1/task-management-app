export interface TaskModel {
  id: number
  title: string
  description: string
  category: number
  status: string
  priority: string
  user?: number
  username?: string
  targetTime: string
}
