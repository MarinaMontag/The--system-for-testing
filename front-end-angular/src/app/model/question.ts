import {Answer} from './answer';

export class Question{
  constructor(public id: number,
              public testId: number,
              public text: string,
              public answerList: Answer[]) {
  }
}
