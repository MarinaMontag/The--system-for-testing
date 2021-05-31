import {Component, OnInit} from '@angular/core';
import {TestService} from '../test.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CreatedTest} from '../model/created-test';
import {HttpErrorResponse} from '@angular/common/http';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit{
  test: CreatedTest;
  result: number;
  visible = true;
  goBack(): void{
    this.router.navigate(['/subject/' + this.test.testInfo.subjectId]);
  }
  constructor(
    private service: TestService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    console.log(this.test);
    this.route.params.subscribe(
      params => {
        this.service.getTest(params.id).subscribe(
          res => {
            this.test = new CreatedTest(res.testInfo, res.questions);
          },
          error => {
            if (error instanceof HttpErrorResponse){
              if (error.status === 401){
                this.router.navigate(['/login']);
              }
            }
          }
        );
      }
    );
  }
  onSelect(questionNum: number, answerNum: number): void{
    const id = this.test.questions[questionNum].answerList[answerNum].id;
    this.test.questions[questionNum].answerList.forEach((answer) => {
        answer.selected = answer.id === id;
      }
    );
  }
  setResult(): void{
    this.result = 0;
    this.test.questions.forEach((question) =>
    {
      question.answerList.forEach((answer) =>
      {
        if (answer.selected === answer.correctness && answer.correctness === true) {
          this.result++;
        }
      });
    });
    this.visible = false;
  }
  setTestComponentVisible(): void{
    this.visible = true;
  }
}
