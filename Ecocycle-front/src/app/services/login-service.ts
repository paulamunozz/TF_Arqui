import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user';
import {Auth} from '../model/auth';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private url = environment.apiUrl + '/security';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  authenticate(user:User){
    return this.httpClient.post<Auth>(this.url + '/authenticate', user);
  }
}
