import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Question} from '../model/question';
import {CreatedTest} from '../model/created-test';
import {Answer} from '../model/answer';
import {Test} from '../model/test';
import {TestService} from '../test.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-create-test',
  templateUrl: './create-test.component.html',
  styleUrls: ['./create-test.component.css']
})
export class CreateTestComponent implements OnInit {
  subjectId: number;
  questionForm: FormGroup;
  testInfoForm: FormGroup;
  test: CreatedTest;
  enterTestInfo = true;
  enterQuestions = false;
  constructor(private fb: FormBuilder,
              private http: TestService,
              private route: ActivatedRoute,
              private router: Router){
    this.questionForm =  this.createQuestionNewFormGroup();
    this.testInfoForm = this.createTestInfoFormGroup();
    this.route.params.subscribe(params => {
      this.subjectId = params.id;
    });
  }
  cancel(): void{
    this.test = new CreatedTest(new Test(null, this.subjectId, '', ''),
      []);
    this.router.navigate(['/subject/' + this.subjectId]);
  }
  createQuestionNewFormGroup(): FormGroup{
    return this.fb.group({
      question: ['', [Validators.required]],
      answers: this.fb.array([
        this.fb.control(''),
        this.fb.control('')
      ]),
      indexOfCorrectAnswer: ['1', [Validators.required]]
    });
  }
  createTestInfoFormGroup(): FormGroup{
    return this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }
  get answers(): FormArray {
    return this.questionForm.get('answers') as FormArray;
  }
  addAnswer(): void {
    this.answers.push(this.fb.control(''));
  }
  addQuestion(): void{
    const question = new Question(null, null, '', []);
    question.text = this.questionForm.controls.question.value;
    question.answerList = [];
    const answersArray = this.questionForm.controls.answers.value;
    for (const answer of answersArray){
      question.answerList.push(new Answer(null, null, answer, null));
    }
    this.setAnswersCorrectness(question);
    this.test.questions.push(question);
    this.questionForm = this.createQuestionNewFormGroup();
  }

  setAnswersCorrectness(question: Question): void{
    for (let i = 0; i < question.answerList.length; i++){
      if ((i + 1) === +this.questionForm.controls.indexOfCorrectAnswer.value){
        question.answerList[i].correctness = true;
      }
      else {
        question.answerList[i].correctness = false;
      }
    }
  }

  addQuestions(): void{
    this.enterTestInfo = false;
    this.enterQuestions = true;
    this.test.testInfo.name = this.testInfoForm.controls.name.value;
    this.test.testInfo.description = this.testInfoForm.controls.description.value;
  }
  save(): void{
    this.addQuestion();
    this.http.sendTest(this.test).subscribe(
      resp => console.log(resp),
      error => console.log(error)
    );
    this.test = new CreatedTest(new Test(null, this.subjectId, '', ''),
      []);
    this.router.navigate(['/subject/' + this.subjectId]);
  }

  ngOnInit(): void {
    this.test = new CreatedTest(new Test(null, this.subjectId, '', ''),
      []);
  }

}
