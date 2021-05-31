import {Question} from './question';
import {Test} from './test';

export class CreatedTest{
  constructor(public testInfo: Test,
              public questions: Question[]) {
  }
}
