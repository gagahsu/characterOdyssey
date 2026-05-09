import { Routes } from '@angular/router';
import { authGuard } from '../../core/auth/auth.guard';

export const learnRoutes: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    children: [
      {
        path: 'character-table',
        loadComponent: () =>
          import('./character-table/character-table').then(m => m.CharacterTableComponent),
      },
      { path: '', redirectTo: 'character-table', pathMatch: 'full' },
    ],
  },
];
