import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubjService {
  url = 'http://localhost:8081/subjects';
  constructor(private http: HttpClient) {}
  getSubjects(): Observable<any>{
   return this.http.get(this.url);
  }
}

