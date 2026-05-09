import { Injectable } from '@angular/core';
import { Howl } from 'howler';

export type Language = 'zhuyin' | 'en' | 'jp' | 'kr';

@Injectable({ providedIn: 'root' })
export class AudioService {
  private sprites: Partial<Record<Language, Howl>> = {};

  loadSprite(lang: Language, src: string, sprite: Record<string, [number, number]>): void {
    if (this.sprites[lang]) return;
    this.sprites[lang] = new Howl({ src: [src], sprite });
  }

  play(lang: Language, soundId: string): void {
    this.sprites[lang]?.play(soundId);
  }
}
