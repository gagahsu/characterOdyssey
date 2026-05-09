import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ProfileService } from '../profile/profile.service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

export const unlockGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const http = inject(HttpClient);
  const profile = inject(ProfileService);
  const router = inject(Router);
  const levelId = route.paramMap.get('levelId');
  const profileId = profile.activeProfile()?.id;

  if (!profileId || !levelId) return router.createUrlTree(['/game']);

  return http.get<{ unlocked: boolean }>(`/api/progress/${profileId}/unlocked/${levelId}`).pipe(
    map(({ unlocked }) => unlocked || router.createUrlTree(['/game'])),
    catchError(() => of(router.createUrlTree(['/game']))),
  );
};
