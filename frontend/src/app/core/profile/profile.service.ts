import { Injectable, signal } from '@angular/core';

export interface Profile {
  id: string;
  name: string;
  avatarUrl?: string;
}

@Injectable({ providedIn: 'root' })
export class ProfileService {
  readonly activeProfile = signal<Profile | null>(null);

  setActive(profile: Profile): void {
    this.activeProfile.set(profile);
  }

  clear(): void {
    this.activeProfile.set(null);
  }
}
