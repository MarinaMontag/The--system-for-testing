import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreatedTest} from './model/created-test';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  getTestsUrl = 'http://localhost:8081/tests';
  testInfoUrl = 'http://localhost:8081/questions';
  constructor(private http: HttpClient) { }
  getTests(id: number): Observable<any>{
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(this.getTestsUrl, {params});
  }
  getTest(id: number): Observable<any>{
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(this.testInfoUrl, {params});
  }
  sendTest(test: CreatedTest): Observable<any>{
    return this.http.post(this.testInfoUrl, JSON.stringify(test));
  }
}
