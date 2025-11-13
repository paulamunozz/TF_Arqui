import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user';
import {Auth} from '../model/auth';
import {map, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private url = environment.apiUrl + '/security';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  login(user: User): Observable<any> {
    console.log("Enviando:", user)
    return this.httpClient.post(this.url + "/authenticate", user,
      {observe: 'response'}).pipe(map((response) => {
        const body = response.body;
        console.log("Body:", body)
        const headers = response.headers;
        const bearerToken = headers.get('Authorization')!;
        const token = bearerToken.replace('Bearer ', '');
        console.log("Authorization:", bearerToken)
        localStorage.setItem('token', token);
        return body;
      }
    ));
  }

  getToken(){
    return localStorage.getItem('token');
  }
}
