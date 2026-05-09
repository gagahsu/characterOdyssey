import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';

const TOKEN_KEY = 'co_jwt';

@Injectable({ providedIn: 'root' })
export class AuthService {
  readonly isLoggedIn = signal(!!localStorage.getItem(TOKEN_KEY));

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>('/api/auth/login', { username, password }).pipe(
      tap(({ token }) => {
        localStorage.setItem(TOKEN_KEY, token);
        this.isLoggedIn.set(true);
      }),
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    this.isLoggedIn.set(false);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }
}
