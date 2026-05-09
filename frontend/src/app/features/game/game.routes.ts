import { Routes } from '@angular/router';
import { authGuard } from '../../core/auth/auth.guard';
import { unlockGuard } from '../../core/auth/unlock.guard';

export const gameRoutes: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    children: [
      {
        path: '',
        loadComponent: () => import('./map/game-map').then(m => m.GameMapComponent),
      },
      {
        path: 'quiz/:levelId',
        canActivate: [unlockGuard],
        loadComponent: () => import('./quiz/quiz').then(m => m.QuizComponent),
      },
    ],
  },
];
