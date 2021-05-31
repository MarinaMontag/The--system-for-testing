import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TestService} from '../test.service';
import {Test} from '../model/test';
import {Role} from '../model/role';
import jwtDecode from 'jwt-decode';
import {AuthGuard} from '../auth.guard';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})
export class TestListComponent implements OnInit{
  tests: Test[] = [];
  subjectId: number;
  role: Role;
  constructor(
    private route: ActivatedRoute,
    private testService: TestService,
    private router: Router,
    private guard: AuthGuard,
    private authService: AuthService
  ) {
    route.params.subscribe(params => {
      this.subjectId = params.id;
    });
  }
ngOnInit(): void {
    this.getTests();
    if (this.role === undefined) {
    // @ts-ignore
    const decodeRole = jwtDecode(this.authService.getToken()).sub;
    if (decodeRole === 'STUDENT') {
      this.role = Role.Student;
    }
    else if (decodeRole === 'TUTOR') {
      this.role = Role.Tutor;
    }
    else
    {
      this.role = null;
    }
  }
}
  getTests(): void {
    this.testService.getTests(this.subjectId)
      .subscribe(
        tests => this.tests = tests.testList,
        error => console.log(error)
      );
  }

  passTest(id: number): void{
    this.router.navigate(['/test/' + id]);
  }
  isTutor(): boolean{
    if (this.role != null) {
      // tslint:disable-next-line:triple-equals
    return this.role.valueOf() == Role.Tutor;
    }
    else {
      return false;
    }
  }
  openCreateTestComponent(): void{
    this.router.navigate(['/create/' + this.subjectId]);
  }
}
