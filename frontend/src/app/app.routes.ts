import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'learn', pathMatch: 'full' },
  {
    path: 'login',
    loadChildren: () => import('./features/auth/auth.routes').then(m => m.authRoutes),
  },
  {
    path: 'learn',
    loadChildren: () => import('./features/learn/learn.routes').then(m => m.learnRoutes),
  },
  {
    path: 'game',
    loadChildren: () => import('./features/game/game.routes').then(m => m.gameRoutes),
  },
  { path: '**', redirectTo: 'learn' },
];
