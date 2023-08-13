import {Pipe, PipeTransform} from "@angular/core";

@Pipe({ name: 'replace' })
export class StringReplacePipe implements PipeTransform {
  transform(value: string, toReplace: string, replacement: string): string {
    if (!value) return '';
    return value.replace(new RegExp(toReplace, 'g'), replacement);
  }
}
