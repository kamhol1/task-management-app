import {NoteModel} from "./note.model";

export interface TaskDetailsModel {
  id: number
  title: string
  description: string
  categoryId: number
  categoryName: string
  status: string
  priority: string
  userId?: number
  username?: string
  targetTime: string
  createdOn: string
  notes: NoteModel[]
}
