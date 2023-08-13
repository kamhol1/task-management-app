import {NoteReadModel} from "../note/note-read.model";

export interface TaskDetailsModel {
  id: number;
  title: string;
  description: string;
  categoryId: number;
  category: string;
  status: string;
  priority: string;
  targetTime: string;
  createdOn: string;
  notes: NoteReadModel[];
}
